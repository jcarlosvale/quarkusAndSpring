package com.study.quarkus;

import com.study.quarkus.dto.AlunoRequest;
import com.study.quarkus.dto.ErrorResponse;
import com.study.quarkus.service.AlunoService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/alunos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class AlunoResource {

    private final AlunoService service;

    @GET
    public Response list() {
        final var response = service.retrieveAll();

        return Response.ok(response).build();
    }

    @POST
    public Response save(final AlunoRequest request) {
        try {
            final var response = service.save(request);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(response)
                    .build();

        } catch(ConstraintViolationException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponse.createFromValidation(e))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {

        final var response = service.getById(id);

        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{id}/tutor/{idProfessor}")
    public Response updateTitular(@PathParam("id") int idAluno, @PathParam("idProfessor") int idProfessor) {
        final var response = service.updateTutor(idAluno, idProfessor);

        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }
}
