package org.formation.service;


import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import org.formation.domain.*;

import java.time.Instant;
import java.util.List;

@ApplicationScoped
@WithTransaction
public class OrderService {

    private final OrderRepository orderRepository;
    private final EventRepository eventRepository;
    private final ReactiveValueCommands<String, Order> cache;

    public OrderService(OrderRepository orderRepository, EventRepository eventRepository,
                        ReactiveRedisDataSource redis) {
        this.orderRepository = orderRepository;
        this.eventRepository = eventRepository;
        this.cache = redis.value(Order.class);
    }

    public Uni<Order> findById(long id) {
        String key = "order:" + id;
        return cache.get(key)
                .onItem().ifNull().switchTo(() ->
                        orderRepository.findById(id)
                                .onItem().ifNull().failWith(() -> new EntityNotFoundException(id + " introuvable"))
                                .call(o -> cache.setex(key, 60, o)));
    }

    public Uni<Order> createOrder(List<OrderItem> lineItems, Address deliveryAddress, PaymentInformation paymentInformation) {
        DeliveryInformation df = new DeliveryInformation();
        df.setAddress(deliveryAddress);

        Order order = Order.builder().date(Instant.now())
                .paymentInformation(paymentInformation)
                .deliveryInformation(df)
                .orderItems(lineItems).build();

        return orderRepository.persistAndFlush(order)
                .chain(persistedOrder -> {

                    Event event = new Event();
                    event.setEntityId(persistedOrder.getId().toString());
                    event.setEventName("ORDER_CREATED");
                    event.setDate(Instant.now());

                    return eventRepository.persist(event)
                            .replaceWith(persistedOrder);
                });
    }
}
