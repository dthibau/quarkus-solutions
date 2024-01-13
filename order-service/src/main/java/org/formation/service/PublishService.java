package org.formation.service;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.formation.domain.event.OrderEvent;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PublishService {
    
    @Channel("orders")
    Emitter<OrderEvent> orderEventEmitter;

    public void publish(OrderEvent orderEvent) {
        orderEventEmitter.send(orderEvent);
    }
}
