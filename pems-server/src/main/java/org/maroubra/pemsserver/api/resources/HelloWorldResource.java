package org.maroubra.pemsserver.api.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.maroubra.pemsserver.api.models.HelloWorldResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Api(value = "Hello")
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

    @GET
    @ApiOperation(value = "Say hello to PEMS")
    public HelloWorldResponse helloWorld() {
        return new HelloWorldResponse();
    }
}
