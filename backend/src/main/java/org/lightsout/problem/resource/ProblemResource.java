package org.lightsout.problem.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.lightsout.problem.dto.CreateProblemRequest;
import org.lightsout.problem.service.ProblemService;

import java.util.UUID;

@Path("/problems")
public class ProblemResource {

    @Inject
    ProblemService problemService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProblems(@QueryParam("page") int pageIndex, @QueryParam("size") int pageSize) {
        return Response.ok(problemService.getProblems(pageIndex, pageSize)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProblem(@PathParam("id") UUID id) {
        return Response.ok(problemService.getProblem(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProblem(@Valid CreateProblemRequest request) {
        problemService.createProblem(request);
        return Response.status(Response.Status.CREATED).build();
    }

}
