package org.maroubra.pemsserver.api.resources;

import io.swagger.annotations.*;
import org.maroubra.pemsserver.api.models.sensors.requests.CreateSensorRequest;
import org.maroubra.pemsserver.api.models.sensors.requests.UpdateSensorRequest;
import org.maroubra.pemsserver.api.models.sensors.responses.SensorHistoryResponse;
import org.maroubra.pemsserver.monitoring.MonitoringService;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.maroubra.pemsserver.utilities.RxUtils.fromObservable;

@Api(value = "Sensors")
@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
public class SensorsResource {

    private static final Logger log = LoggerFactory.getLogger(SensorsResource.class);

    private final MonitoringService monitoringService;

    @Inject
    public SensorsResource(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GET
    @ApiOperation(value = "List sensors")
    public void listSensors(@Suspended AsyncResponse asyncResponse) {
        CompletableFuture<List<SensorConfig>> sensorsFuture = fromObservable(monitoringService.listSensors());

        sensorsFuture.thenApply(asyncResponse::resume).exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));
    }

    @POST
    @ApiOperation(value = "Create Sensor")
    public Response createSensor(@ApiParam(name = "JSON body", required = true) CreateSensorRequest createSensorRequest) {
        monitoringService.createSensor(new SensorConfig(createSensorRequest.getId(), createSensorRequest.getType(), createSensorRequest.getConfigMap()));
        return Response.ok().build();
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
