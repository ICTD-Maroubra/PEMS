package org.maroubra.pemsserver.jersey;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.maroubra.pemsserver.bindings.BluetoothBindings;
import org.maroubra.pemsserver.bindings.MongoBindings;
import org.maroubra.pemsserver.bindings.ObjectMapperFactory;
import org.maroubra.pemsserver.bindings.ServerBindings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JerseyApplication extends ResourceConfig {

    private static Logger log = LoggerFactory.getLogger(JerseyApplication.class);

    public JerseyApplication() {
        log.info("setting up hk2");
        packages("org.maroubra.pemsserver", "org.maroubra.pemsserver.jersey");

        JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();
        jacksonJaxbJsonProvider.setMapper(new ObjectMapperFactory().buildObjectMapper());
        register(jacksonJaxbJsonProvider);

        register(new ServerBindings());
        register(new MongoBindings());
        register(new BluetoothBindings());
    }
}
