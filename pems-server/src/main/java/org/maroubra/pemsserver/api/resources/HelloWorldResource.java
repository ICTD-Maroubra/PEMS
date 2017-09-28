package org.maroubra.pemsserver.api.resources;

import org.maroubra.pemsserver.api.models.HelloWorldResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

    @GET
    public HelloWorldResponse helloWorld() {
        return new HelloWorldResponse();
    }
}
