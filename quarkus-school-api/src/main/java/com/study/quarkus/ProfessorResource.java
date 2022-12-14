package com.study.quarkus;

import com.study.quarkus.dto.ProfessorDto;
import com.study.quarkus.service.ProfessorService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/professores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfessorResource {

    private final ProfessorService service;

    @Inject
    public ProfessorResource(ProfessorService service) {
        this.service = service;
    }


    @GET
    public Response listProfessors() {
        final List<ProfessorDto> professorDtoList = service.retrieveAll();

        return Response.ok(professorDtoList).build();
    }

    @GET
    @Path("/{id}")
    public Response getProfessor(@PathParam("id") int id) {

        final ProfessorDto professorDto = service.getById(id);

        return Response.ok(professorDto).build();
    }

    @POST
    public Response saveProfessor(final ProfessorDto professor) {

        service.save(professor);

        return Response
                .status(Response.Status.CREATED)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProfessor(@PathParam("id") int id, ProfessorDto professor) {

        service.update(id, professor);

        return Response
                .ok(professor)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeProfessor(@PathParam("id") int id) {

        service.delete(id);

        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }
}
