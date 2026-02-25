package org.formation.web.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.core.Response;
import org.formation.domain.Livraison;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@ApplicationScoped
public class ExceptionHandler {

    @ServerExceptionMapper
    RestResponse<String> mapNoResultException(NoResultException ex) {
        return RestResponse.status(Response.Status.NOT_FOUND, "Unknown entity" + ex.getMessage());
    }

}
