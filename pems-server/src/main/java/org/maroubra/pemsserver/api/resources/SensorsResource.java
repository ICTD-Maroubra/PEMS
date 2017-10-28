package org.maroubra.pemsserver.api.resources;

import com.sun.corba.se.impl.corba.CORBAObjectImpl;
import io.swagger.annotations.*;
import org.maroubra.pemsserver.api.models.sensors.requests.CreateSensorRequest;
import org.maroubra.pemsserver.api.models.sensors.requests.UpdateSensorRequest;
import org.maroubra.pemsserver.api.models.sensors.responses.SensorDescriptorResponse;
import org.maroubra.pemsserver.api.models.sensors.responses.SensorHistoryResponse;
import org.maroubra.pemsserver.monitoring.MonitoringService;
import org.maroubra.pemsserver.monitoring.Sensor;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.omg.CORBA.Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
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
    @Path("{id}/{dataSize}/history")
    @ApiOperation(value = "Get a sensors history")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Specified sensor does not exist")
    })
    public SensorHistoryResponse getHistory(@PathParam("id") String id, @PathParam("dataSize") int size) {
        List<SensorLog> sensorLogs = null;
        if (id.equals("kutlu")) {
            ArrayList<SensorLog> sensorLogskutlu = new ArrayList<>();
            Map<String, java.lang.Object> kutluMap = new HashMap<>();
            kutluMap.put("kutlu", "something here");
            kutluMap.put("kutlu1", "anotherthing here");
            Map<String, java.lang.Object> kutluMap2 = new HashMap<>();
            kutluMap2.put("kutlu2", "someotherthing here");
            kutluMap2.put("kutlu3", "here");

            sensorLogskutlu.add(new SensorLog(id, kutluMap, LocalDateTime.now()));
            sensorLogskutlu.add(new SensorLog(id, kutluMap2, LocalDateTime.now()));

            sensorLogs = sensorLogskutlu;
        }
        else {
            sensorLogs = monitoringService.getSensorLogs(id, size);
        }
        return SensorHistoryResponse.create(sensorLogs);
    }
}
