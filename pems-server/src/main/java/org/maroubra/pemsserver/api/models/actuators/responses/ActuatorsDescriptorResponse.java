package org.maroubra.pemsserver.api.models.actuators.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.maroubra.pemsserver.control.AbstractActuator;
import org.maroubra.pemsserver.monitoring.ConfigDescriptor;

public class ActuatorsDescriptorResponse {

    @JsonProperty("id")
    public String id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("config")
    public ConfigDescriptor config;

    public static ActuatorsDescriptorResponse create(AbstractActuator actuator) {
        ActuatorsDescriptorResponse actuatorsResponse = new ActuatorsDescriptorResponse();
        actuatorsResponse.id = String.valueOf(actuator.getId());
        actuatorsResponse.name = actuator.getName();
        actuatorsResponse.config = null;
        return actuatorsResponse;
    }
}
