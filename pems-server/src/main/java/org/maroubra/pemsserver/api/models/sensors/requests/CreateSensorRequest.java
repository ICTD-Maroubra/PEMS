package org.maroubra.pemsserver.api.models.sensors.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Map;

@JsonAutoDetect
public class CreateSensorRequest {

    private String type;
    private Map<String, Object> configMap;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setConfigMap(Map<String, Object> configMap) {
        this.configMap = configMap;
    }

    public Map<String, Object> getConfigMap() {
        return configMap;
    }
}
