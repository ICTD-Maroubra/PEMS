package org.maroubra.pemsserver.api.models.actuators.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.maroubra.pemsserver.control.AbstractActuator;
import org.maroubra.pemsserver.monitoring.ConfigDescriptor;

public class ActuatorsListResponse {

    @JsonProperty("id")
    public String id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("config")
    public ConfigDescriptor config;

    public static ActuatorsListResponse create(AbstractActuator actuator) {
        ActuatorsListResponse actuatorsListResponse = new ActuatorsListResponse();
        actuatorsListResponse.id = String.valueOf(actuator.getId());
        actuatorsListResponse.name = actuator.getName();
        actuatorsListResponse.config = null;
        return actuatorsListResponse;
    }
}
