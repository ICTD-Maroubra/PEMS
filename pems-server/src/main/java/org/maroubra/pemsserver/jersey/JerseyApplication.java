package org.maroubra.pemsserver.jersey;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.maroubra.pemsserver.bindings.ObjectMapperFactory;
import org.maroubra.pemsserver.bindings.ServerBindings;
import org.maroubra.pemsserver.configuration.ServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JerseyApplication extends ResourceConfig {

    private static Logger log = LoggerFactory.getLogger(JerseyApplication.class);

    public JerseyApplication(ServerConfiguration serverConfiguration) {
        log.info("setting up hk2");
        packages("org.maroubra.pemsserver", "org.maroubra.pemsserver.jersey");

        JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();
        jacksonJaxbJsonProvider.setMapper(new ObjectMapperFactory().buildObjectMapper());
        register(jacksonJaxbJsonProvider);
        register(new ServerBindings());

        // Swagger
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setTitle("PEMS Server API");
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost(serverConfiguration.host + ":" + serverConfiguration.port);
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("org.maroubra.pemsserver.api.resources");
        beanConfig.setServletConfig(null);
        beanConfig.setScan(true);

        register(ApiListingResource.class);
        register(SwaggerSerializers.class);
    }
}
