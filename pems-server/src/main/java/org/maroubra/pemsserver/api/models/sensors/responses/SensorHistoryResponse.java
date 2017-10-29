package org.maroubra.pemsserver.api.models.sensors.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.maroubra.pemsserver.monitoring.SensorLog;


public class SensorHistoryResponse {
    @JsonProperty("id")
    public String id;

    @JsonProperty("valueType")
    public Object type;

    @JsonProperty("value")
    public Object data;

    @JsonProperty("timeStamp")
    public String time;

    public static SensorHistoryResponse create(SensorLog sensorLog) {
        SensorHistoryResponse sensorHistoryResponse = new SensorHistoryResponse();
        sensorHistoryResponse.id = sensorLog.getSensorId();
        sensorHistoryResponse.type = sensorLog.getAttributeValue().keySet();
        sensorHistoryResponse.data = sensorLog.getAttributeValue().values();
        sensorHistoryResponse.time = sensorLog.getTimestamp().toString();
        return sensorHistoryResponse;
    }
}
