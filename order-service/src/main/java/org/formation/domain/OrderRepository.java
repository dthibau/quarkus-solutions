package org.formation.domain;

import java.time.Instant;
import java.util.List;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {
    
    public Uni<List<Order>> findOrdersWithDiscount() {
        return find("discount IS NOT NULL").list();
    }

    public Uni<List<Order>> findOrdersPosteriorTo(Instant instant) {
        return find("date > ?1", instant).list();
    }

}
