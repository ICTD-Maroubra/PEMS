package org.maroubra.pemsserver.configuration;

import com.github.joschi.jadconfig.Parameter;

public class ServerConfiguration {
    @Parameter(value = "host", required = true)
    public String host;

    @Parameter(value = "port", required = true)
    public String port;

    public String fullHost() {
        return "http://" + host + ":" + port + "/";
    }
}
