package org.formation.service;


import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/notifications")
@RegisterRestClient(configKey = "notification-api")
public interface NotificationService {
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    NotificationDto sendMail(NotificationDto notification);
}
