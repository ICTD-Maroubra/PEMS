package org.maroubra.pemsserver.monitoring.utsapi;

import io.reactivex.Flowable;
import org.maroubra.pemsserver.monitoring.AbstractSensor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WebSensor extends AbstractSensor{

    private static final Logger log = LoggerFactory.getLogger(WebSensor.class);

    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private int pollIntervalMinutes = 60;
    private String family;
    private String subSensor;
    private String sensor;
    private UtsWebApi service;
    private Retrofit retrofit;


    public WebSensor(String family, String sensor, String subSensor) {
        this.family = family;
        this.subSensor = subSensor;
        this.sensor = sensor;
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
        parameters.put("rSubSensor", subSensor);
        parameters.put("rSensor", sensor);
        parameters.put("rFamily", family);
        parameters.put("rToDate", toDate.toString());
        parameters.put("rFromDate", fromDate.toString());
        return parameters;
    }

    public List<String[]> pollSensor() {
        setDatesPollInterval();
        List<String[]> data = null;
        Call<List<String[]>> call = service.getHcJsonData(getQueryParameters());
        try {
            data = call.execute().body();
            log.info(data.get(0)[0] + " Data: " + data.get(0)[1]);
        } catch (IOException e) {
            e.getMessage();
            log.warn("This sensor may not be returning data please check the sensor api webpage.");
        }
        return data;
    }

    @Override
    public String toString() {
        return "Sensor Family: " + family + " Sensor: " + sensor + " Sub Sensor: " + subSensor;
    }

    @Override
    protected boolean start() {
        if (pollSensor() != null){
            return true;
        }
        return false;
    }

    @Override
    protected boolean stop() {
        return false;
    }

    @Override
    protected Flowable<SensorLog> logs() {
        return null;
    }
}