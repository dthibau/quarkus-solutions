package org.formation.service;


import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.formation.domain.*;

import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Uni<Order> createOrder(List<OrderItem> lineItems, Address deliveryAddress, PaymentInformation paymentInformation) {
        DeliveryInformation df = new DeliveryInformation();
        df.setAddress(deliveryAddress);

        Order order = Order.builder().date(Instant.now())
                .paymentInformation(paymentInformation)
                .deliveryInformation(df)
                .orderItems(lineItems).build();

        return orderRepository.persist(order);
    }
}
