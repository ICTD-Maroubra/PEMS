package org.maroubra.pemsserver.api.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "Sensors")
@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
public class SensorsResource {

    @GET
    @ApiOperation(value = "List sensors")
    public Response listSensors() {
        throw new UnsupportedOperationException();
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value = "Update a sensors configuration")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Specified actuator does not exist")
    })
    public void updateSensor(@PathParam("id") String id) {
        throw new UnsupportedOperationException();
    }

    @GET
    @Path("{id}/history")
    @ApiOperation(value = "Get a sensors history")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Specified actuator does not exist")
    })
    public Response getHistory(@PathParam("id") String id) {
        throw new UnsupportedOperationException();
    }
}
