package org.maroubra.pemsserver.api.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "System")
@Path("/system")
@Produces(MediaType.APPLICATION_JSON)
public class SystemResource {

    @GET
    @ApiOperation(value = "Get system info")
    public Response info() {
        throw new UnsupportedOperationException();
    }

    @GET
    @Path("config")
    @ApiOperation(value = "Get system configuration")
    public Response config() {
        throw new UnsupportedOperationException();
    }

    @GET
    @Path("status")
    @ApiOperation(value = "Get system status")
    public Response status() {
        throw new UnsupportedOperationException();
    }
}
