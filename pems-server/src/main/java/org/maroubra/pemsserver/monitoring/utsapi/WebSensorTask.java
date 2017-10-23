package org.maroubra.pemsserver.monitoring.utsapi;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
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
    private WebSensorConfig config;
    private UtsWebApi webApi;
    private ZonedDateTime fromDate;
    private ZonedDateTime toDate;
    private int pollIntervalMinutes;
    private final FlowableProcessor<SensorLog> processor;


    WebSensorTask(WebSensorConfig webSensorConfig, int pollIntervalMinutes, FlowableProcessor<SensorLog> processor, UtsWebApi webApi) {
        this.config = webSensorConfig;
        this.processor = processor;
        this.webApi = webApi;
        this.pollIntervalMinutes = pollIntervalMinutes;
    }

    public void setDatesPollInterval() {
        fromDate =  ZonedDateTime.now(ZoneOffset.UTC).minusMinutes(pollIntervalMinutes).withNano(0);
        toDate = ZonedDateTime.now(ZoneOffset.UTC).withNano(0);
    }

    private Call<List<String[]>> callWebApi() {
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("rFromDate", fromDate.toString());
        parameters.put("rToDate", toDate.toString());
        parameters.put("rFamily", config.getConfig().get("rFamily"));
        parameters.put("rSensor", config.getConfig().get("rSensor"));
        parameters.put("rSubSensor", config.getConfig().get("rSubSensor"));
        return  webApi.getHcJsonData(parameters);
    }

    public List<String[]> pollSensor() {
        setDatesPollInterval();
        List<String[]> data = null;
        Call<List<String[]>> call = callWebApi();
        try {
            log.info(call.request().url().encodedQuery());
            log.info(call.request().url().toString());
            data = call.execute().body();
            for (String[] dataArray: data) {
                SensorLog sensorLog = new SensorLog(
                        config.id(),
                        ImmutableMap.of(config.getConfig().get("rSubSensor"), dataArray[1]),
                        ZonedDateTime.ofInstant(Instant.ofEpochSecond( Long.getLong(dataArray[0])), ZoneId.systemDefault() ));
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
