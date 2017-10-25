package org.maroubra.pemsserver.api.models.sensors.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Map;

@JsonAutoDetect
public class CreateSensorRequest {

    private String id;
    private String type;
    private Map<String, String> configMap;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setConfigMap(Map<String, String> configMap) {
        this.configMap = configMap;
    }

    public Map<String, String> getConfigMap() {
        return configMap;
    }
}
