package org.formation.service;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/notifications")
@RegisterRestClient(configKey = "notification-api")
public interface NotificationService {
    
    @POST
    Uni<NotificationDto> sendMail(NotificationDto notification);
}
