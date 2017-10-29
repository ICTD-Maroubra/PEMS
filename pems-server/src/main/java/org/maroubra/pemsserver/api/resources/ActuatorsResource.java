package org.maroubra.pemsserver.api.resources;

import io.swagger.annotations.*;
import org.maroubra.pemsserver.api.models.actuators.requests.UpdateActuatorRequest;
import org.maroubra.pemsserver.api.models.actuators.responses.ActuatorsDescriptorResponse;
import org.maroubra.pemsserver.control.ControlService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Actuators")
@Path("/actuators")
@Produces(MediaType.APPLICATION_JSON)
public class ActuatorsResource {

    private final ControlService controlService;

    @Inject
    public ActuatorsResource(ControlService controlService) {
        this.controlService = controlService;
    }

    @GET
    @ApiOperation(value = "List actuators")
    public List<ActuatorsDescriptorResponse> listActuators() {
        return controlService.listActuators().stream().map(ActuatorsDescriptorResponse::create).collect(Collectors.toList());
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
