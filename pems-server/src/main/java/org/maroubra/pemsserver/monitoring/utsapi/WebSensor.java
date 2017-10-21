package org.maroubra.pemsserver.monitoring.utsapi;

import com.google.common.collect.ImmutableMap;
import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import org.maroubra.pemsserver.monitoring.AbstractSensor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class WebSensor extends AbstractSensor{

    private static final Logger log = LoggerFactory.getLogger(WebSensor.class);
    private final PublishProcessor<SensorLog> sensorLogPublisher = PublishProcessor.create();

    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private int pollIntervalMinutes = 60;
    private WebSensorConfig config;
    private UtsWebApi webApi;

    public WebSensor(WebSensorConfig config, UtsWebApi webApi) {
        this.config = config;
        this.webApi = webApi;
    }

    public void setPollIntervalMinutes(int pollIntervalMinutes) {
        this.pollIntervalMinutes = pollIntervalMinutes;
    }

    public void setDatesPollInterval() {
        fromDate = LocalDateTime.now().minusMinutes(pollIntervalMinutes).withNano(0);
        toDate = LocalDateTime.now().withNano(0);
    }

    public void setDates(LocalDateTime fromDate, LocalDateTime toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    private Map<String, String> getQueryParameters() {
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("rSubSensor", config.getConfig().get("rSubSensor"));
        parameters.put("rSensor", config.getConfig().get("rSensor"));
        parameters.put("rFamily", config.getConfig().get("rFamily"));
        parameters.put("rToDate", toDate.toString());
        parameters.put("rFromDate", fromDate.toString());
        return parameters;
    }

    public List<String[]> pollSensor() {
        setDatesPollInterval();
        List<String[]> data = null;
        Call<List<String[]>> call = webApi.getHcJsonData(getQueryParameters());
        try {
            data = call.execute().body();
            for (String[] dataArray: data) {
                SensorLog sensorLog = new SensorLog(
                        config.id(),
                        ImmutableMap.of(config.getConfig().get("rSubSensor"), dataArray[1]),
                        ZonedDateTime.ofInstant(Instant.ofEpochSecond( Long.getLong(dataArray[0])), ZoneId.systemDefault() ));
                //processor.onNext(sensorLog);

            }
        } catch (IOException e) {
            e.getMessage();
            log.warn("This sensor may not be returning data please check the sensor api webpage.");
        }
        
        return data;
    }

    @Override
    public String toString() {
        return "Sensor Family: " + config.getConfig().get("rFamily") + " Sensor: " + config.getConfig().get("rSensor") + " Sub Sensor: " + config.getConfig().get("rSubSensor");
    }

    @Override
    protected boolean start() { return false; }

    @Override
    protected boolean stop() {
        return false;
    }

    @Override
    protected Flowable<SensorLog> logs() { return sensorLogPublisher.onBackpressureLatest();  }
}