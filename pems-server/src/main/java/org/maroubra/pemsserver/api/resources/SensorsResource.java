package org.maroubra.pemsserver.api.resources;

import io.swagger.annotations.*;
import org.maroubra.pemsserver.api.models.sensors.requests.CreateSensorRequest;
import org.maroubra.pemsserver.api.models.sensors.requests.UpdateSensorRequest;
import org.maroubra.pemsserver.api.models.sensors.responses.SensorDescriptorResponse;
import org.maroubra.pemsserver.api.models.sensors.responses.SensorHistoryResponse;
import org.maroubra.pemsserver.monitoring.MonitoringService;
import org.maroubra.pemsserver.monitoring.Sensor;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.maroubra.pemsserver.utilities.RxUtils.fromObservable;

@Api(value = "Sensors")
@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
public class SensorsResource {

    private static final Logger log = LoggerFactory.getLogger(SensorsResource.class);
    private static final int DEFAULT_SENSOR_HISTORY_SIZE = 5;

    private final MonitoringService monitoringService;

    @Inject
    public SensorsResource(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GET
    @ApiOperation(value = "List sensors")
    public void listSensors(@Suspended AsyncResponse asyncResponse) throws InterruptedException, ExecutionException{
        CompletableFuture<List<SensorConfig>> sensorsFuture = fromObservable(monitoringService.listSensors());

        sensorsFuture.thenApply(asyncResponse::resume)
                .exceptionally(e -> {
                    log.warn("Exception while listing sensors", e);
                    return asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).build());
                });
    }

    @POST
    @ApiOperation(value = "Create Sensor")
    public void createSensor(@Suspended AsyncResponse asyncResponse, @ApiParam(name = "JSON body", required = true) CreateSensorRequest createSensorRequest) {
        monitoringService.createSensor(new SensorConfig(createSensorRequest.getType(), createSensorRequest.getConfigMap()))
                .subscribe(() -> asyncResponse.resume(Response.ok().build()));
    }

    @GET
    @Path("types")
    @ApiOperation(value = "Available Sensor Types")
    public List<SensorDescriptorResponse> sensorTypes() {
        List<Sensor.Descriptor> descriptors = monitoringService.listSensorTypes();
        return descriptors.stream().map(SensorDescriptorResponse::create).collect(Collectors.toList());
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
            @ApiResponse(code = 404, message = "Specified sensor does not exist")
    })
    public List<SensorHistoryResponse> getHistory(@PathParam("id") String id, @QueryParam("dataSize") int size) {
        if (size == 0) {
            size = DEFAULT_SENSOR_HISTORY_SIZE;
        }
        List<SensorLog> sensorLogs = monitoringService.getSensorLogs(id, size);
        return sensorLogs.stream().map(SensorHistoryResponse::create).collect(Collectors.toList());
    }


    @GET
    @Path("{id}/stop")
    @ApiOperation(value = "Stops a sensor given its id.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Specified sensor does not exist")
    })
    public boolean stopSensor(@PathParam("id")String id) {
        return monitoringService.stopSensor(id);
    }
}
