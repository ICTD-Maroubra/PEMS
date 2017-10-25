package org.maroubra.pemsserver.monitoring.utsapi;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;
import org.maroubra.pemsserver.monitoring.Sensor;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.maroubra.pemsserver.monitoring.annotations.FactoryClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class WebSensor implements Sensor {

    private static final Logger log = LoggerFactory.getLogger(WebSensor.class);

    protected static final String CONFIG_KEY_FAMILY = "family";
    protected static final String CONFIG_KEY_SENSOR = "sensor";
    protected static final String CONFIG_KEY_SUB_SENSOR = "sub_sensor";

    private final UtsWebApi webAPi;
    private final PublishProcessor<SensorLog> sensorLogPublisher = PublishProcessor.create();
    private int pollIntervalMinutes = 60;
    private SensorConfig config;
    private TimerTask webSensorTask;
    private Timer timer;

    @AssistedInject
    public WebSensor(@Assisted SensorConfig config, UtsWebApi webApi) {
        this.config = config;
        this.webAPi = webApi;
    }

    public  void setPollIntervalMinutes(int pollIntervalMinutes) { this.pollIntervalMinutes = pollIntervalMinutes; }

    @Override
    public String toString() {
        return "Sensor Family: " + config.getConfigMap().get(CONFIG_KEY_FAMILY) + " Sensor: " + config.getConfigMap().get(CONFIG_KEY_SENSOR)
                + " Sub Sensor: " + config.getConfigMap().get(CONFIG_KEY_SUB_SENSOR);
    }

    @Override
    public boolean start() {
        timer = new Timer(true);
        webSensorTask = new WebSensorTask(config, pollIntervalMinutes, sensorLogPublisher, webAPi);
        timer.schedule(webSensorTask, 0, pollIntervalMinutes * 60 * 1000);
        return true;
    }

    @Override
    public boolean stop() {
        timer.cancel();
        return true;
    }

    @Override
    public Flowable<SensorLog> logs() { return sensorLogPublisher.onBackpressureLatest();  }

    @Override
    public SensorConfig getConfig() {
        return config;
    }

    @FactoryClass
    public interface Factory extends Sensor.Factory<WebSensor> {
        @Override
        WebSensor create(@Assisted SensorConfig config);
    }
}