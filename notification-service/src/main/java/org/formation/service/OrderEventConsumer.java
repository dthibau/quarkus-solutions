package org.formation.service;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.formation.domain.Event;

@ApplicationScoped
public class OrderEventConsumer {

    @Incoming("orders-in")
    public Uni<Void> consume(Event event) {
        switch (event.getEventName()) {
            case "ORDER_CREATED" -> Log.info("Order created " + event);
            case "ORDER_CANCELED" -> Log.info("Order cancelled " + event);
            default -> Log.error("Event Unknown");
        };
        return Uni.createFrom().voidItem();
    }
}
