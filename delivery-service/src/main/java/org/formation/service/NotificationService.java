package org.formation.service;


import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/notifications")
@RegisterRestClient(configKey = "notification-api")
@ClientHeaderParam(name = "X-api-key", value = "${notification.token}")
public interface NotificationService {
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    NotificationDto sendMail(NotificationDto notification);

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    Uni<NotificationDto>  sendMailAsync(NotificationDto notification);
}
