package org.formation.service;


import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.quarkus.oidc.client.reactive.filter.OidcClientRequestReactiveFilter;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/notifications")
@RegisterRestClient(configKey = "notification-api")
@RegisterProvider(OidcClientRequestReactiveFilter.class)
public interface NotificationService {
    
    @POST
    Uni<NotificationDto> sendMail(NotificationDto notification);
}
