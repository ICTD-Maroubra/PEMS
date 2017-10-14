package org.maroubra.pemsserver.monitoring.sensortag;

import org.maroubra.pemsserver.monitoring.AbstractSensor;
import org.maroubra.pemsserver.monitoring.SensorConfig;

public class SensortagSensorConfig implements SensorConfig {

    private final String id;
    private String address;

    public SensortagSensorConfig(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public Class<? extends AbstractSensor> sensorType() {
        return SensortagSensor.class;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
