package org.maroubra.pemsserver.api.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class HelloWorldResponse {

    @JsonProperty("message")
    public String message = "Hello from PEMS!";
}
