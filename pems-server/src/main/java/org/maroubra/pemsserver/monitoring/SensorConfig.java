package org.maroubra.pemsserver.monitoring;

import org.maroubra.pemsserver.database.CollectionName;

@CollectionName("sensor_config")
public interface SensorConfig {

    String getId();
    void setId(String id);

    String type();
}
