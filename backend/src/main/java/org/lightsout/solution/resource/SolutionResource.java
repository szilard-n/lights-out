package org.lightsout.solution.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.lightsout.solution.service.SolutionService;

import java.util.UUID;

@Path("/solutions")
public class SolutionResource {

    @Inject
    SolutionService solutionService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSolutions(@QueryParam("page") int pageIndex, @QueryParam("size") int pageSize) {
        return Response.ok(solutionService.getSolutions(pageIndex, pageSize)).build();
    }

    @GET
    @Path("/problem/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSolution(@PathParam("id") UUID problemId) {
        return Response.ok(solutionService.getSolution(problemId)).build();
    }

}
