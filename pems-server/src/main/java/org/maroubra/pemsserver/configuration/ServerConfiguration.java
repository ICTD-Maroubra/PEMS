package org.maroubra.pemsserver.configuration;

import com.github.joschi.jadconfig.Parameter;

public class ServerConfiguration {
    @Parameter(value = "host")
    public String host = "0.0.0.0";

    @Parameter(value = "port")
    public String port = "9005";

    public String fullHost() {
        return "http://" + host + ":" + port + "/";
    }
}
