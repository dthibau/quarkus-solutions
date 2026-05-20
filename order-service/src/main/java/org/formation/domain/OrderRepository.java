package org.formation.domain;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order>{

    public Uni<List<Order>> withDiscount() {
        return list("discount > 0");
    }
    public Uni<List<Order>> createdAfter(Instant from) {
        return find("date > ?1", from).list();
    }
}
