package org.maroubra.pemsserver.api.resources;

import com.google.common.io.Resources;

import javax.activation.MimetypesFileTypeMap;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.requireNonNull;

@Path("/api-browser")
public class ApiBrowserResource {

    private final MimetypesFileTypeMap mimeTypes;
    private final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    @Inject
    public ApiBrowserResource(MimetypesFileTypeMap mimeTypes) {
        this.mimeTypes = requireNonNull(mimeTypes);
    }

    @GET
    public Response root(@Context HttpHeaders httpHeaders) throws IOException {
        final String index = index(httpHeaders);
        return Response.ok(index, MediaType.TEXT_HTML_TYPE)
                .header(HttpHeaders.CONTENT_LENGTH, index.length())
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("index.html")
    public String index(@Context HttpHeaders httpHeaders) throws IOException {
        final URL templateUrl = this.getClass().getResource("/swagger-ui/index.html");
        return Resources.toString(templateUrl, StandardCharsets.UTF_8);
    }


    @GET
    @Path("/{route: .*}")
    public Response asset(@PathParam("route") String route) throws IOException {
        // Directory traversal should not be possible but just to make sure..
        if (route.contains("..")) {
            throw new BadRequestException("Not allowed to access parent directory");
        }

        final URL resource = classLoader.getResource("swagger-ui/" + route);
        if (null != resource) {
            try {
                final byte[] resourceBytes = Resources.toByteArray(resource);
                String type = mimeTypes.getContentType(route);
                return Response.ok(resourceBytes, type)
                        .header("Content-Length", resourceBytes.length)
                        .build();
            } catch (IOException e) {
                throw new NotFoundException("Couldn't load " + resource, e);
            }
        } else {
            throw new NotFoundException("Couldn't find " + route);
        }
    }
}
