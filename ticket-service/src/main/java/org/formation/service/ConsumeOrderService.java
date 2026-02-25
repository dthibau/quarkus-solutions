package org.formation.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.formation.domain.event.OrderEvent;
import org.formation.domain.event.OrderEventType;

@ApplicationScoped
@RequiredArgsConstructor
public class ConsumeOrderService {

    private final TicketService ticketService;

    @Incoming("orders")
    public void onOrderEvent(OrderEvent orderEvent) {
        if (OrderEventType.CREATED == orderEvent.getType() ) {
            Log.info("Order created with ID: " + orderEvent.getOrder().getId());
            ticketService.createTicket(orderEvent.getOrder().getId(),
            orderEvent.getOrder().getProductRequests());
        }
    }
}
