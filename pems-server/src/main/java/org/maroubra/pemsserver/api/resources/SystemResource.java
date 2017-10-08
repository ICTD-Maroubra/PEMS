package org.maroubra.pemsserver.api.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.maroubra.pemsserver.api.models.system.responses.SystemConfigResponse;
import org.maroubra.pemsserver.api.models.system.responses.SystemInfoResponse;
import org.maroubra.pemsserver.api.models.system.responses.SystemStatusResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Api(value = "System")
@Path("/system")
@Produces(MediaType.APPLICATION_JSON)
public class SystemResource {

    @GET
    @ApiOperation(value = "Get system info")
    public SystemInfoResponse info() {
        throw new UnsupportedOperationException();
    }

    @GET
    @Path("config")
    @ApiOperation(value = "Get system configuration")
    public SystemConfigResponse config() {
        throw new UnsupportedOperationException();
    }

    @GET
    @Path("status")
    @ApiOperation(value = "Get system status")
    public SystemStatusResponse status() {
        throw new UnsupportedOperationException();
    }
}
