package org.maroubra.pemsserver.api.resources;

import io.swagger.annotations.*;
import org.maroubra.pemsserver.api.models.sensors.requests.UpdateSensorRequest;
import org.maroubra.pemsserver.api.models.sensors.responses.SensorHistoryResponse;
import org.maroubra.pemsserver.api.models.sensors.responses.SensorListResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api(value = "Sensors")
@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
public class SensorsResource {

    @GET
    @ApiOperation(value = "List sensors")
    public SensorListResponse listSensors() {
        throw new UnsupportedOperationException();
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value = "Update a sensors configuration")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Specified actuator does not exist")
    })
    public void updateSensor(@PathParam("id") String id, @ApiParam(name = "JSON body", required = true) UpdateSensorRequest usr) {
        throw new UnsupportedOperationException();
    }

    @GET
    @Path("{id}/history")
    @ApiOperation(value = "Get a sensors history")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Specified actuator does not exist")
    })
    public SensorHistoryResponse getHistory(@PathParam("id") String id) {
        throw new UnsupportedOperationException();
    }
}
