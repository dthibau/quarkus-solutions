package org.formation.web;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.formation.domain.event.OrderEvent;

import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/")
public class OrderResource {

    @Channel("orders")
	Multi<OrderEvent> orderEvents;
    
    @GET
	@Path("/incoming-orders")
	@Produces(MediaType.SERVER_SENT_EVENTS) 
	public Multi<OrderEvent> stream() {
	    return orderEvents; 
	}
}
