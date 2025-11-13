package org.formation.service;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.formation.domain.*;

import java.util.List;

@ApplicationScoped
@WithTransaction
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Uni<Order> createOrder(List<OrderItem> lineItems,
                                  Address deliveryAddress,
                                  PaymentInformation paymentInformation) {
        Order order = Order.builder()
                .orderItems(lineItems)
                .deliveryInformation(DeliveryInformation.builder()
                        .address(deliveryAddress)
                        .build())
                .paymentInformation(paymentInformation)
                .build();

        return orderRepository.persist(order);
    }

    public Uni<List<Order>> findAll() {
        return orderRepository.listAll();
    }
}
