package org.formation.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.quarkus.oidc.token.propagation.reactive.AccessTokenRequestReactiveFilter;



@Path("/notifications")
@RegisterRestClient(configKey = "notification-api")
@RegisterProvider(AccessTokenRequestReactiveFilter.class)
public interface NotificationService {

	@POST
    Courriel sendMail(Courriel couriel);
	
//	@POST
//	@Path("/reactive")
//    Uni<Courriel> sendMailReactive(Courriel couriel);
}
