package org.formation.web;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import javax.swing.text.html.parser.Entity;

public class GlobalExceptionMapper {

    @ServerExceptionMapper
    public RestResponse<ErrorDto> handleNotFound(EntityNotFoundException ex) {
        return RestResponse.status(Response.Status.NOT_FOUND,
                ErrorDto.builder().type("ENTITY_NOT_FOUND").detailMessage(ex.getMessage()).build());
    }
}
