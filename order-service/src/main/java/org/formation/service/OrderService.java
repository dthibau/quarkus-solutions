package org.formation.service;


import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.formation.domain.*;

import java.time.Instant;
import java.util.List;

@ApplicationScoped
@WithTransaction
public class OrderService {

    private final OrderRepository orderRepository;
    private final EventRepository eventRepository;

    public OrderService(OrderRepository orderRepository, EventRepository eventRepository) {
        this.orderRepository = orderRepository;
        this.eventRepository = eventRepository;
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
