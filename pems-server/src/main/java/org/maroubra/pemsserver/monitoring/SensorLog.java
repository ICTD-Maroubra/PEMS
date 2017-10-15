package org.maroubra.pemsserver.monitoring;

import com.google.common.collect.ImmutableMap;
import org.maroubra.pemsserver.database.CollectionName;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;

@CollectionName("sensor_log")
public class SensorLog {

    private String sensorId;
    private ZonedDateTime timestamp;
    private ImmutableMap<String, Object> attributeValue;

    public SensorLog(String sensorId, Map<String, Object> attributeValue) {
        this(sensorId, attributeValue, ZonedDateTime.now(ZoneOffset.UTC));
    }

    public SensorLog(String sensorId, Map<String, Object> attributeValue, ZonedDateTime timestamp) {
        this.sensorId = sensorId;
        this.attributeValue = ImmutableMap.copyOf(attributeValue);
        this.timestamp = timestamp;
    }
}
