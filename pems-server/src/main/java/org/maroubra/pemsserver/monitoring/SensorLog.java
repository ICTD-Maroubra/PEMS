package org.maroubra.pemsserver.monitoring;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

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
