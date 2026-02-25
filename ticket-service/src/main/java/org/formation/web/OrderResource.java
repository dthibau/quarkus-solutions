package org.formation.web;

import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.formation.domain.event.OrderEvent;

@Path("/incoming-orders")
public class OrderResource {

    @Channel("orders")
	Multi<OrderEvent> orderEvents;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<OrderEvent> pushOrderEvent() {
        return orderEvents;
    }
}
