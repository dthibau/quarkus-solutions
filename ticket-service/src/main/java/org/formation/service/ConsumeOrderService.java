package org.formation.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.formation.domain.event.OrderEvent;
import org.formation.domain.event.OrderEventType;

@ApplicationScoped
public class ConsumeOrderService {

    @Inject
    TicketService ticketService;
    @Incoming("orders")
    public void consumeOrderEvent(OrderEvent orderEvent) {
        if (OrderEventType.CREATED == orderEvent.getType() ) {
            Log.info("Order created with ID: " + orderEvent.getOrder().getId());
            ticketService.createTicket(orderEvent.getOrder().getId(),
                    orderEvent.getOrder().getProductRequests());
        }
}   }
