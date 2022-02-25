package cat.ambatlle.tasks.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Just a sample REST resource for testing purposes
 */
@Path("/sample")
@Produces("application/json")
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
