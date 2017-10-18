package org.maroubra.pemsserver.api.resources;

import io.swagger.annotations.*;
import org.maroubra.pemsserver.api.models.actuators.requests.UpdateActuatorRequest;
import org.maroubra.pemsserver.api.models.actuators.responses.ActuatorsListResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api(value = "Actuators")
@Path("/actuators")
@Produces(MediaType.APPLICATION_JSON)
public class ActuatorsResource {

    @GET
    @ApiOperation(value = "List actuators")
    public ActuatorsListResponse listActuators() {
        throw new UnsupportedOperationException();
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value = "Update an actuator's configuration")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Specified actuator does not exist")
    })
    public void updateConfig(@PathParam("id") String id, @ApiParam(value = "JSON body", required = true) UpdateActuatorRequest uar) {
        throw new UnsupportedOperationException();
    }
}
