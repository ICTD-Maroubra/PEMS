package org.maroubra.pemsserver.monitoring.nordic;

import org.maroubra.pemsserver.monitoring.AbstractSensor;
import org.maroubra.pemsserver.monitoring.SensorConfig;

/**
 * Configuration for the Thingy52 sensor
 */
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

    /**
     * MAC address of Thingy52
     * @return MAC address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the MAC address
     * @param address MAC address
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
