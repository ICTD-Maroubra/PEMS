package org.maroubra.pemsserver.monitoring.utsapi;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;

import java.io.IOException;
import java.time.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class WebSensorTask extends TimerTask {

    private static final Logger log = LoggerFactory.getLogger(WebSensor.class);
    private SensorConfig config;
    private UtsWebApi webApi;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private int pollIntervalMinutes;
    private final FlowableProcessor<SensorLog> processor;


    WebSensorTask(SensorConfig webSensorConfig, int pollIntervalMinutes, FlowableProcessor<SensorLog> processor, UtsWebApi webApi) {
        this.config = webSensorConfig;
        this.processor = processor;
        this.webApi = webApi;
        this.pollIntervalMinutes = pollIntervalMinutes;
    }

    public void setDatesPollInterval() {
        fromDate =  LocalDateTime.now().minusMinutes(pollIntervalMinutes).withNano(0);
        toDate = LocalDateTime.now().withNano(0);
    }

    private Map<String,String> getQueryParameters() {
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("rFromDate", fromDate.toString());
        parameters.put("rToDate", toDate.toString());
        parameters.put("rFamily", config.getProperty(WebSensor.CONFIG_KEY_FAMILY));
        parameters.put("rSensor", config.getProperty(WebSensor.CONFIG_KEY_SENSOR));
        parameters.put("rSubSensor", config.getProperty(WebSensor.CONFIG_KEY_SUB_SENSOR));
        return  parameters;
    }

    public List<String[]> pollSensor() {
        setDatesPollInterval();
        List<String[]> data = null;
        Call<List<String[]>> call = webApi.getHcJsonData(getQueryParameters());
        try {
            data = call.execute().body();
            for (String[] dataArray: data) {
                log.info("Time: " + dataArray[0] + " Data: " + dataArray[1]);
                SensorLog sensorLog = new SensorLog(
                        config.getId(),
                        ImmutableMap.of(config.getProperty(WebSensor.CONFIG_KEY_SUB_SENSOR), dataArray[1]),
                        LocalDateTime.ofEpochSecond(Long.valueOf(dataArray[0]), 0, ZoneOffset.UTC));
                processor.onNext(sensorLog);

            }
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("This sensor may not be returning data please check the sensor api webpage.");
        }
        return data;
    }

    @Override
    public void run() {
        pollSensor();
    }
}
