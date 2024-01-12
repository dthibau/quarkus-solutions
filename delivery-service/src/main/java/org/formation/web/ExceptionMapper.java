package org.formation.web;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

public class ExceptionMapper {
    
    @ServerExceptionMapper
    public RestResponse<ErrorDto> mapException(EntityNotFoundException x) {
        return RestResponse.status(Response.Status.NOT_FOUND, 
        ErrorDto.builder().message(x.getMessage()).severity("WARN").status(Response.Status.NOT_FOUND.getStatusCode()).build());
    }
}
