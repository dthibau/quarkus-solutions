package org.formation.service;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.formation.service.dto.NotificationDto;

@RegisterRestClient(configKey = "notification-api")
@Path("/notifications")
@ClientHeaderParam(name = "X-api-key", value = "${notification.token}")
public interface NotificationService {

    @POST
    NotificationDto sendMail(NotificationDto notification);
}
