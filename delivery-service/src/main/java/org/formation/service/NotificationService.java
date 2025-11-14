package org.formation.service;

import io.smallrye.common.annotation.Blocking;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import lombok.extern.java.Log;
import org.eclipse.microprofile.faulttolerance.*;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/notifications")
@RegisterRestClient(configKey = "notification-api")
@ClientHeaderParam(name = "X-api-key", value = "${notification.token}")
public interface NotificationService {

    @POST
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(NotificationServiceFallback.class)
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio=0.75, delay = 10000)
    NotificationDto sendMail(NotificationDto notification);
    

    @Log
    public static class NotificationServiceFallback implements FallbackHandler<NotificationDto> {

        @Override
        public NotificationDto handle(ExecutionContext executionContext) {
            log.info("NotificationService is down, executing fallback method.");
            return null;
        }
    }
}
