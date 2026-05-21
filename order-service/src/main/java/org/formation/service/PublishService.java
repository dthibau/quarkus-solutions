package org.formation.service;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.formation.domain.Event;

@ApplicationScoped
public class PublishService {
    @Channel("orders-out")
    MutinyEmitter<Event> emitter;

    public Uni<Void> publish(Event event) {
        return emitter.send(event);
    }
}
