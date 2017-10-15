package org.maroubra.pemsserver.api.models.sensors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Service;
import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class AbstractUtsWebSensorService {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private int pollIntervalMinutes;
    private String family;
    private String subSensor;
    private String sensor;
    private String apiLink = "http://eif-research.feit.uts.edu.au/api/";
    private String dataFormat = "json/";
    private static Logger log = LoggerFactory.getLogger(AbstractUtsWebSensorService.class);
    private String sensorLog = "";
    private Timer timer;
    private TimerTask timerTask;


    /*example implementation - stick it to the main
        AbstractUtsWebSensorService abstractUtsWebSensor = new AbstractUtsWebSensorService("wasp","ES_B_11_429_3E90","BAT",60);
        abstractUtsWebSensor.setDates();
        abstractUtsWebSensor.buildHTTPQuery();
        log.info(abstractUtsWebSensor.monitor());
     */

    public AbstractUtsWebSensorService(String family, String sensor, String subSensor, int pollIntervalMinutes) {
        this.family = family;
        this.subSensor = subSensor;
        this.sensor = sensor;
        this.pollIntervalMinutes = pollIntervalMinutes;
    }

    public void setPollIntervalMinutes(int pollIntervalMinutes) {
        this.pollIntervalMinutes = pollIntervalMinutes;
    }

    public void setDates() {
        fromDate = LocalDateTime.now().minusMinutes(pollIntervalMinutes).withNano(0);
        toDate = LocalDateTime.now().withNano(0);
    }

    public void setDatesSeconds() {
        fromDate = LocalDateTime.now().minusSeconds(pollIntervalMinutes).withNano(0);
        toDate = LocalDateTime.now().withNano(0);
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


    public void monitorSensor() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    setDates();
                    sensorLog += ""+ (new WebSensorTask(buildHTTPQuery()).call()).toString();
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            }
        };
        timer.schedule(timerTask, pollIntervalMinutes * 60 * 1000);
    }
}
