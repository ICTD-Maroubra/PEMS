package org.maroubra.pemsserver.monitoring.nordic;

import org.maroubra.pemsserver.monitoring.AbstractSensor;
import org.maroubra.pemsserver.monitoring.SensorConfig;

public class Thingy52SensorConfig implements SensorConfig {

    private final String id;
    private String address;

    public Thingy52SensorConfig(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public Class<? extends AbstractSensor> sensorType() {
        return Thingy52Sensor.class;
    }
}
