package org.maroubra.pemsserver.monitoring.utsapi;

import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;
import org.maroubra.pemsserver.monitoring.AbstractSensor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class WebSensor extends AbstractSensor{

    private static final Logger log = LoggerFactory.getLogger(WebSensor.class);
    private final UtsWebApi webAPi;
    private final PublishProcessor<SensorLog> sensorLogPublisher = PublishProcessor.create();
    private int pollIntervalMinutes = 60;
    private WebSensorConfig config;
    private TimerTask webSensorTask;
    private Timer timer;

    public WebSensor(WebSensorConfig config, UtsWebApi webApi) {
        this.config = config;
        this.webAPi = webApi;
    }

    public  void setPollIntervalMinutes(int pollIntervalMinutes) { this.pollIntervalMinutes = pollIntervalMinutes; }

    @Override
    public String toString() {
        return "Sensor Family: " + config.getConfig().get("rFamily") + " Sensor: " + config.getConfig().get("rSensor")
                + " Sub Sensor: " + config.getConfig().get("rSubSensor");
    }

    @Override
    protected boolean start() {
        timer = new Timer(true);
        webSensorTask = new WebSensorTask(config,pollIntervalMinutes, sensorLogPublisher, webAPi);
        timer.schedule(webSensorTask, 0, pollIntervalMinutes * 60 * 1000);
        return true;
    }

    @Override
    protected boolean stop() {
        timer.cancel();
        return true;
    }

    @Override
    protected Flowable<SensorLog> logs() { return sensorLogPublisher.onBackpressureLatest();  }
}