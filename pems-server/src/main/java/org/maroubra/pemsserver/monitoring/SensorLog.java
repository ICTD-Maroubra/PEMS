package org.maroubra.pemsserver.monitoring;

import org.maroubra.pemsserver.database.CollectionName;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@CollectionName("sensor_log")
public class SensorLog {

    private String sensorId;
    private ZonedDateTime timestamp;

    public SensorLog(String sensorId) {
        this(sensorId, ZonedDateTime.now(ZoneOffset.UTC));
    }

    public SensorLog(String sensorId, ZonedDateTime timestamp) {
        this.sensorId = sensorId;
        this.timestamp = timestamp;
    }
}
