package org.maroubra.pemsserver.jersey;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.Contact;
import io.swagger.models.Info;
import io.swagger.models.License;
import org.glassfish.jersey.server.ResourceConfig;
import org.maroubra.pemsserver.api.resources.HelloWorldResource;
import org.maroubra.pemsserver.bindings.ObjectMapperFactory;
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

        // Swagger
        Info info = new Info();
        info.setTitle("PEMS Server API");
        info.setVersion("1.0.0");
        info.setLicense(new License().name("MIT"));
        info.setContact(new Contact().url("https://github.com/ICTD-Maroubra/PEMS"));
        info.setDescription("This following document describes the HTTP(S) API for interacting with the PEMS server.");

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setInfo(info);
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage(HelloWorldResource.class.getPackage().getName());

        new SwaggerContextService().withSwaggerConfig(beanConfig).withBasePath(beanConfig.getBasePath()).initConfig(beanConfig.getSwagger());

        register(ApiListingResource.class);
        register(SwaggerSerializers.class);
    }
}
