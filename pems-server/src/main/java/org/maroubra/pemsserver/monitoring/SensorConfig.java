package org.maroubra.pemsserver.monitoring;

import org.maroubra.pemsserver.database.CollectionName;

@CollectionName("sensor_config")
public interface SensorConfig {

    String id();
    Class<? extends AbstractSensor> sensorType();
}
