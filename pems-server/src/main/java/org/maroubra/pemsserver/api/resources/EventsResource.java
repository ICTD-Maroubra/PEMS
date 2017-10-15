package org.maroubra.pemsserver.api.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.maroubra.pemsserver.api.models.events.responses.EventListResponse;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Api(value = "Events")
@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
public class EventsResource {

    @GET
    @ApiOperation(value = "List events")
    public EventListResponse listEvents() {
        throw new UnsupportedOperationException();
    }

    @POST
    @Path("{id}/acknowledge")
    @ApiOperation(value = "Acknowledge an event")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Specified event does not exist")
    })
    public void acknowledgeEvent() {
        throw new UnsupportedOperationException();
    }
}
