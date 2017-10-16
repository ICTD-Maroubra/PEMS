package org.maroubra.pemsserver.api.models.sensors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.FutureTask;

public class WebSensor {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private int pollIntervalMinutes;
    private String family;
    private String subSensor;
    private String sensor;
    private String apiLink = "http://eif-research.feit.uts.edu.au/api/";
    private String dataFormat = "json/";
    private static Logger log = LoggerFactory.getLogger(WebSensor.class);


    /*example implementation - stick it to the main
        WebSensor webSensor = new WebSensor("wasp","ES_B_11_429_3E90","BAT",60);
        log.info(webSensor.monitorSensor());
     */

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


    public String buildHTTPQuery() {
        String partialQuery = "";
        Map<String,String> parameters = new LinkedHashMap<>();
        parameters.put("rFromDate",fromDate.toString());
        parameters.put("rToDate",toDate.toString());
        parameters.put("rFamily",family);
        parameters.put("rSensor",sensor);
        parameters.put("rSubSensor", subSensor);

        try {
            partialQuery = QueryBuilder.QueryBuilder(parameters);
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        String query = apiLink + "" + dataFormat + "" + partialQuery;
        return query;
    }

    public String pollSensor() {
        setDatesPollInterval();
        String data;
        WebSensorTask webSensorTask = new WebSensorTask(buildHTTPQuery());
        try {
            data = webSensorTask.getData();
        }
        catch (Exception e) {
            log.info(e.getMessage());
            data = "Error";
        }
        return data;
    }

    @Override
    public String toString(){
        return "Sensor Family: " + family + " Sensor: " + sensor + " Sub Sensor: " + subSensor;
    }
}
