package com.study;

import com.study.dto.CorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/cores")
public class GreetingResource {

    private final Map<Integer, CorDto> cores = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(GreetingResource.class);


    //@GET
    //@Produces(MediaType.APPLICATION_JSON)
    public Response listCores() {
        log.info("Listing cores");
        if (cores.isEmpty()) {
            return Response.ok().build();
        }
        else {
            return Response
                    .ok(new ArrayList<>(cores.values()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCor(@PathParam("id") int id) {
        log.info("Getting cor {}", id);
        var cor = cores.get(id);
        if (Objects.isNull(cor)) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        else {
            return Response
                    .ok(cor)
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCor(@QueryParam("prefixo") String prefixo) {
        log.info("Getting cor with prefix {}", prefixo);
        if(Objects.isNull(prefixo)) return listCores();
        var selectedCores = cores.values().stream()
                        .filter(cor -> cor.getDescricao()
                        .startsWith(prefixo))
                        .collect(Collectors.toList());
       return Response
               .ok(selectedCores)
               .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCor(final CorDto cor) {
        if (Objects.isNull(cor)) {
            log.error("Invalid body - null");
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        else {
            cores.put(cor.getId(), cor);
            log.info("Inserted a new color {}", cor);
            return Response
                    .status(Response.Status.CREATED)
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCor(@PathParam("id") int id, CorDto corDto) {
        log.info("Updating cor {}", id);
        var cor = cores.get(id);
        if (Objects.isNull(cor)) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        else {
            cores.put(id, corDto);
            return Response
                    .ok(corDto)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeCor(@PathParam("id") int id) {
        log.info("Deleting cor {}", id);
        var cor = cores.get(id);
        if (Objects.isNull(cor)) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        else {
            cores.remove(id);
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        }
    }
}