package org.maroubra.pemsserver.api.models.sensors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class WebSensorService implements Runnable{

    private Map<WebSensor,ArrayList<String>> webSensors = null;
    private int globalPollIntervalSeconds = 0;
    private boolean terminated = false;

    private static Logger log = LoggerFactory.getLogger(WebSensor.class);

    /* To test to code - put it to main
        //put this in class
        private static boolean started = false;
        private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        private static WebSensorService webSensorService;
        //put this in method
        if (!started){
            webSensorService = new WebSensorService();
            webSensorService.addSensor(new WebSensor("wasp","ES_B_11_429_3E90","BAT"));
            webSensorService.addSensor(new WebSensor("wasp","ES_B_11_429_3E90","TCA"));
            webSensorService.setPollIntervalMinutes(10);
            scheduler.scheduleAtFixedRate(webSensorService,0,5, TimeUnit.SECONDS);
            started = true;
        }
     */

    public WebSensorService() {
        webSensors = new HashMap<>();
    }

    public void addSensor(WebSensor webSensor) { webSensors.put(webSensor, new ArrayList<>()); }

    public void removeSensor(WebSensor webSensor) { webSensors.remove(webSensor); }

    public Set<WebSensor> getWebSensors() { return webSensors.keySet(); }

    public void setPollIntervalMinutes(int pollIntervalMinutes) {
        for(WebSensor webSensor : webSensors.keySet()) {
            webSensor.setPollIntervalMinutes(pollIntervalMinutes);
            globalPollIntervalSeconds = pollIntervalMinutes * 60;
        }
    }

    public void setGlobalPollInterval(int globalPollIntervalSeconds) {this.globalPollIntervalSeconds = globalPollIntervalSeconds;}

    public void pollAllSensors() {
        for (Map.Entry<WebSensor,ArrayList<String>> entry : webSensors.entrySet()) {
            ArrayList<String> sensorData = entry.getValue();
            sensorData.add(entry.getKey().pollSensor());
            entry.setValue(sensorData);
        }
    }

    public void terminate() {
        terminated = true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Map.Entry<WebSensor,ArrayList<String>> entry : webSensors.entrySet()) {
            stringBuilder.append(System.lineSeparator() + "Sensor Details: " + entry.getKey().toString() +
                    System.lineSeparator() + "Sensor Data:" + entry.getValue().toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public void run() {
        pollAllSensors();
        log.info(toString());
    }



}
