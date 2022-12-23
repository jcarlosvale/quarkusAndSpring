package com.study.quarkus;

import com.study.quarkus.service.SchoolJwtService;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/jwt")
@RequiredArgsConstructor
public class SchoolJwtResource {

    private final SchoolJwtService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getJwt() {
        String jwt = service.generateJwt();
        return Response.ok(jwt).build();
    }

}
