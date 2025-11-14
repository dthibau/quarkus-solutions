package org.formation.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.formation.domain.event.OrderEvent;
import io.smallrye.reactive.messaging.kafka.Record;

@ApplicationScoped
public class PublishService {

    @Inject
    @Channel("orders")
    Emitter<Record<Long, OrderEvent>> emitter;

    public void publishOrderEvent(OrderEvent orderEvent) {
        emitter.send(Record.of(orderEvent.getOrder().getId(), orderEvent));
    }
}
