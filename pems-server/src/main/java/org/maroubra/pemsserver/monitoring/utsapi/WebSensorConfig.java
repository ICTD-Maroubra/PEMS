package org.maroubra.pemsserver.monitoring.utsapi;

import com.google.common.collect.ImmutableMap;
import org.maroubra.pemsserver.monitoring.AbstractSensor;
import org.maroubra.pemsserver.monitoring.SensorConfig;

import java.util.Map;

public class WebSensorConfig implements SensorConfig{

    private final String id;
    private String family;
    private String subSensor;
    private String sensor;

    public WebSensorConfig(String id) {
        this.id = id;
    }

    @Override
    public String id() { return id; }

    @Override
    public Class<? extends AbstractSensor> sensorType() { return WebSensor.class; }

    public Map<String,String> getConfig() {
        return ImmutableMap.of("rFamily", family, "rSensor", sensor, "rSubSensor", subSensor);
    }

    public void setConfig(String family, String sensor , String subSensor) {
        this.family = family;
        this.sensor = sensor;
        this.subSensor = subSensor;
    }
}
