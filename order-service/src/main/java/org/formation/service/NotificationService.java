package org.formation.service;

import io.quarkus.oidc.client.reactive.filter.OidcClientRequestReactiveFilter;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/notifications")
@RegisterRestClient(configKey = "notification-api")
@RegisterProvider(OidcClientRequestReactiveFilter.class)
public interface NotificationService {

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    Uni<NotificationDto> sendMail(NotificationDto notification);
}