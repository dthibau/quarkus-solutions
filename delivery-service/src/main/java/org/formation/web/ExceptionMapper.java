package org.formation.web;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@ApplicationScoped
public class ExceptionMapper {

    @ServerExceptionMapper
    public Response mapEntityNotFoundException(EntityNotFoundException e) {
        ErrorDto dto = ErrorDto.builder()
                .status(Response.Status.NOT_FOUND.getStatusCode())
                .severity("WARN")
                .message(e.getMessage())
                .build();
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(dto)
                .build();
    }
}
