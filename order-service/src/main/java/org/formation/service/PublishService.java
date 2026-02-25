package org.formation.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.formation.domain.event.OrderEvent;

@ApplicationScoped
public class PublishService {

    @Inject
    @Channel("orders")
    Emitter<OrderEvent> emitter;

    public void publishOrderEvent(OrderEvent orderEvent) {
        emitter.send(orderEvent);
    }
}
