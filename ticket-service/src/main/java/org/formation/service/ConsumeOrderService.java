package org.formation.service;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.formation.domain.event.OrderEvent;
import org.formation.domain.event.OrderEventType;

import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

public class ConsumeOrderService {
    
    @Inject
	@Channel("orders")
	Multi<OrderEvent> orderEvents;

    @Inject
    TicketService ticketService;

   void onStart(@Observes StartupEvent ev) {
		Log.info("Subscribing ...");
		orderEvents.subscribe().with(e -> receiveOrderEvent(e));
	}

	public void receiveOrderEvent(OrderEvent orderEvent) {
		Log.info("Receiving message " + orderEvent);
		if (orderEvent.getType().equals(OrderEventType.CREATED)) {
			ticketService.createTicket(orderEvent.getOrder().getId(), orderEvent.getOrder().getProductRequests());
		}
	} 
}
