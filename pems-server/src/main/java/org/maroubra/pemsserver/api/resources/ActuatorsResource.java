package org.maroubra.pemsserver.api.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "Actuators")
@Path("/actuators")
@Produces(MediaType.APPLICATION_JSON)
public class ActuatorsResource {

    @GET
    @ApiOperation(value = "List actuators")
    public Response listActuators() {
        throw new UnsupportedOperationException();
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value = "Update an actuator's configuration")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Specified actuator does not exist")
    })
    public void updateConfig(@PathParam("id") String id) {
        throw new UnsupportedOperationException();
    }
}
