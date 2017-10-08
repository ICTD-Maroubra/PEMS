package org.maroubra.pemsserver.api.resources;

import io.swagger.annotations.*;
import org.maroubra.pemsserver.api.models.alarms.requests.CreateAlarmRequest;
import org.maroubra.pemsserver.api.models.alarms.requests.UpdateAlarmRequest;
import org.maroubra.pemsserver.api.models.alarms.responses.AlarmsListResponse;
import org.maroubra.pemsserver.api.models.alarms.responses.CreatedAlarmResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api(value = "Alarms")
@Path("/alarms")
@Produces(MediaType.APPLICATION_JSON)
public class AlarmsResource {

    @GET
    @ApiOperation(value = "List alarms")
    public AlarmsListResponse listAlarms() {
        throw new UnsupportedOperationException();
    }

    @POST
    @ApiOperation(value = "Create a new alarm condition")
    public CreatedAlarmResponse createAlarm(@ApiParam(value = "JSON body", required = true) CreateAlarmRequest car) {
        throw new UnsupportedOperationException();
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value = "Update an alarm condition")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Specified alarm does not exist")
    })
    public void updateAlarm(@PathParam("id") String id, @ApiParam(value = "JSON body", required = true) UpdateAlarmRequest uar) {
        throw new UnsupportedOperationException();
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(value = "Delete an alarm condition")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Specified alarm does not exist")
    })
    public void deleteAlarm(@PathParam("id") String id) {
        throw new UnsupportedOperationException();
    }

    @POST
    @Path("{id}/acknowledge")
    @ApiOperation(value = "Acknowledge an alarm")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Specified alarm does not exist")
    })
    public void acknowledgeAlarm(@PathParam("id") String id) {
        throw new UnsupportedOperationException();
    }
}
