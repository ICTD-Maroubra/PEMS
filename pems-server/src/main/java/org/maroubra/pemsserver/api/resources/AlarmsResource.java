package org.maroubra.pemsserver.api.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "Alarms")
@Path("/alarms")
@Produces(MediaType.APPLICATION_JSON)
public class AlarmsResource {

    @GET
    @ApiOperation(value = "List alarms")
    public Response listAlarms() {
        throw new UnsupportedOperationException();
    }

    @POST
    @ApiOperation(value = "Create a new alarm condition")
    public Response createAlarm() {
        throw new UnsupportedOperationException();
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value = "Update an alarm condition")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Specified alarm does not exist")
    })
    public void updateAlarm(@PathParam("id") String id) {
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
