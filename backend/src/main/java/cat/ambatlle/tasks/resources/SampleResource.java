package cat.ambatlle.tasks.resources;

import io.swagger.v3.oas.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Just a sample REST resource for testing purposes
 */
@Path("/sample")
@Produces("application/json")
@Tag(name = "Sample")
public class SampleResource {
    /**
     * Testing get endpoint
     *
     * @return an "ok" response, with status 200
     */
    @GET
    @Path("/")
    public Response ask() {
        return Response.ok("ok").build();
    }
}
