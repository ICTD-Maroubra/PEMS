package org.maroubra.pemsserver.api.models.sensors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class WebSensorService implements Runnable{

    private List<WebSensor> webSensors = null;
    private Map<WebSensor,String> webSensorData = null;
    private int globalPollIntervalSeconds = 0;
    private boolean terminated = false;

    private static Logger log = LoggerFactory.getLogger(WebSensor.class);

    /* To test to code - put it to main
        WebSensorService webSensorService = new WebSensorService();
        webSensorService.addSensor(new WebSensor("wasp","ES_B_11_429_3E90","BAT"));
        webSensorService.addSensor(new WebSensor("wasp","ES_B_11_429_3E90","TCA"));
        webSensorService.setPollIntervalMinutes(10);
        //global poll interval can be overwritten to shorten the thread execution for testing purposes
        //global poll interval in seconds = 10
        webSensorService.setGlobalPollInterval(10);
        new Thread(webSensorService).start();

        //webSensorService.terminate();
     */

    public WebSensorService() {
        webSensors = new ArrayList<WebSensor>();
        webSensorData = new HashMap<WebSensor,String>();
    }

    public boolean addSensor(WebSensor webSensor) { return webSensors.add(webSensor); }

    public boolean removeSensor(WebSensor webSensor) { return webSensors.remove(webSensor); }

    public List<WebSensor> getWebSensors() { return webSensors; }

    public void setPollIntervalMinutes(int pollIntervalMinutes) {
        for(WebSensor webSensor : webSensors) {
            webSensor.setPollIntervalMinutes(pollIntervalMinutes);
            globalPollIntervalSeconds = pollIntervalMinutes * 60;
        }
    }

    public void setGlobalPollInterval(int globalPollIntervalSeconds) {this.globalPollIntervalSeconds = globalPollIntervalSeconds;}

    public void pollAllSensors() {
        for (WebSensor webSensor : webSensors) {
            webSensorData.put(webSensor, webSensor.pollSensor());
        }
    }

    public void terminate() {
        terminated = true;
    }

    @Override
    public void run() {
        while (!terminated){
            pollAllSensors();
            try {
                Thread.sleep(globalPollIntervalSeconds * 1000);
            }
            catch (InterruptedException e){
                log.warn("WebSensorService was interrupted!");
            }
        }

    }



}
