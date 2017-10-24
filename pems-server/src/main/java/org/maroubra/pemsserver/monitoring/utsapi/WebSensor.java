package org.maroubra.pemsserver.monitoring.utsapi;

import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;
import org.maroubra.pemsserver.monitoring.Sensor;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.maroubra.pemsserver.monitoring.annotations.ConfigClass;
import org.maroubra.pemsserver.monitoring.annotations.FactoryClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class WebSensor implements Sensor {

    private static final Logger log = LoggerFactory.getLogger(WebSensor.class);
    private final UtsWebApi webAPi;
    private final PublishProcessor<SensorLog> sensorLogPublisher = PublishProcessor.create();
    private int pollIntervalMinutes = 60;
    private Config config;
    private TimerTask webSensorTask;
    private Timer timer;

    @AssistedInject
    public WebSensor(@Assisted SensorConfig config, UtsWebApi webApi) {
        this.config = (Config) config;
        this.webAPi = webApi;
    }

    public  void setPollIntervalMinutes(int pollIntervalMinutes) { this.pollIntervalMinutes = pollIntervalMinutes; }

    @Override
    public String toString() {
        return "Sensor Family: " + config.getConfig().get("rFamily") + " Sensor: " + config.getConfig().get("rSensor")
                + " Sub Sensor: " + config.getConfig().get("rSubSensor");
    }

    @Override
    public boolean start() {
        timer = new Timer(true);
        webSensorTask = new WebSensorTask(config,pollIntervalMinutes, sensorLogPublisher, webAPi);
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

    @FactoryClass
    public interface Factory extends Sensor.Factory<WebSensor> {
        @Override
        WebSensor create(@Assisted SensorConfig config);

        @Override
        Config getConfig();
    }

    @ConfigClass
    public static class Config implements SensorConfig {

        private String id;
        private String family;
        private String subSensor;
        private String sensor;

        @Override
        public String getId() { return id; }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String type() { return WebSensor.class.getCanonicalName(); }

        public Map<String,String> getConfig() {
            return ImmutableMap.of("rFamily", family, "rSensor", sensor, "rSubSensor", subSensor);
        }

        public void setConfig(String family, String sensor , String subSensor) {
            this.family = family;
            this.sensor = sensor;
            this.subSensor = subSensor;
        }
    }
}