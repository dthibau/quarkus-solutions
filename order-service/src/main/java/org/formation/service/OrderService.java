package org.formation.service;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.formation.domain.*;
import org.formation.domain.event.OrderEvent;
import org.formation.domain.event.OrderEventType;

import java.util.List;

@ApplicationScoped
@WithTransaction
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PublishService publishService;

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

        Uni<Order> ret = orderRepository.persist(order).onItem().invoke(savedOrder -> {
            OrderEvent orderEvent = OrderEvent.builder().order(savedOrder).type(OrderEventType.CREATED).build();
            publishService.publishOrderEvent(orderEvent);
        });
        return ret;
    }

    public Uni<List<Order>> findAll() {
        return orderRepository.listAll();
    }
}
