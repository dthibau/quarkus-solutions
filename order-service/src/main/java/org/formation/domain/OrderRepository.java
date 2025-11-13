package org.formation.domain;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order>{

    public Uni<List<Order>> findOrdersPosteriorTo(Instant now) {
        return find("date > ?1", now).list();
    }

    public Uni<List<Order>> findOrdersWithDiscount() {
        return find("discount > ?1", 0).list();
    }
}
