package org.maroubra.pemsserver.api.models.sensors.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import org.maroubra.pemsserver.monitoring.SensorLog;

import java.util.ArrayList;
import java.util.List;

public class SensorHistoryResponse {

    @JsonProperty("valueName")
    public Object[] type;

    @JsonProperty("value")
    public Object[] data;

    @JsonProperty("timeStamp")
    public String[] time;

    public static SensorHistoryResponse create(List<SensorLog> sensorLogList) {
        SensorHistoryResponse sensorHistoryResponse = new SensorHistoryResponse();
        List<List<String>> logType = new ArrayList<>();
        List<List<Object>> logData = new ArrayList<>();
        List<Object> logTime =  new ArrayList<>();;
        for (SensorLog sensorLog : sensorLogList) {
            ImmutableMap log = ImmutableMap.copyOf(sensorLog.getAttributeValue());
            if (log.values().asList().size() == 1) {
                logData.addAll(log.values().asList());
                logType.addAll(log.keySet().asList());
            }
            else {
                logData.add(log.values().asList());
                logType.add(log.keySet().asList());
            }
            logTime.add(sensorLog.getTimestamp().toString());


        }
        sensorHistoryResponse.data = logData.toArray();
        sensorHistoryResponse.type = logType.toArray();
        sensorHistoryResponse.time = logTime.toArray(new String[0]);
        return sensorHistoryResponse;
    }
}
