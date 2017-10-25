package org.maroubra.pemsserver.monitoring;

import com.google.common.collect.ImmutableMap;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.maroubra.pemsserver.database.CollectionName;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@CollectionName("sensor_log")
public class SensorLog {

    private String sensorId;
    private LocalDateTime timestamp;
    private Map<String, Object> attributeValue;

    public SensorLog(String sensorId, Map<String, Object> attributeValue) {
        this(sensorId, attributeValue, LocalDateTime.now());
    }

    public SensorLog(String sensorId, Map<String, Object> attributeValue, LocalDateTime timestamp) {
        this.sensorId = sensorId;
        this.attributeValue = ImmutableMap.copyOf(attributeValue);
        this.timestamp = timestamp;
    }

    public String getSensorId() {
        return sensorId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map<String, Object> getAttributeValue() {
        return ImmutableMap.copyOf(attributeValue);
    }

    @BsonCreator
    public static SensorLog create(@BsonProperty("sensorId") String sensorId, @BsonProperty("timestamp") LocalDateTime timestamp, @BsonProperty("attributeValue") HashMap<String, Object> attributeValue) {
        return new SensorLog(sensorId, attributeValue, timestamp);
    }
}
