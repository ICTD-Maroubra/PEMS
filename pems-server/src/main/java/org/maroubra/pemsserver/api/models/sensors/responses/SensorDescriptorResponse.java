package org.maroubra.pemsserver.api.models.sensors.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.maroubra.pemsserver.monitoring.ConfigDescriptor;
import org.maroubra.pemsserver.monitoring.Sensor;

public class SensorDescriptorResponse {

    @JsonProperty("type")
    public String type;

    @JsonProperty("config")
    public ConfigDescriptor config;

    public static SensorDescriptorResponse create(Sensor.Descriptor descriptor) {
        SensorDescriptorResponse sensorDescriptor = new SensorDescriptorResponse();
        sensorDescriptor.type = descriptor.type();
        sensorDescriptor.config = descriptor.configurationDescriptor();
        return sensorDescriptor;
    }
}
