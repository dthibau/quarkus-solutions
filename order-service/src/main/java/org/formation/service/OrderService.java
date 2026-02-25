package org.formation.service;

import java.util.List;

import org.formation.domain.Address;
import org.formation.domain.DeliveryInformation;
import org.formation.domain.Order;
import org.formation.domain.OrderItem;
import org.formation.domain.OrderRepository;
import org.formation.domain.PaymentInformation;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.formation.domain.event.OrderEvent;
import org.formation.domain.event.OrderEventType;

@ApplicationScoped
public class OrderService {
    
    @Inject
    OrderRepository orderRepository;
    @Inject
    PublishService publishService;

    @WithTransaction
    public Uni<Order> createOrder(List<OrderItem> lineItems, Address deliveryAddress, PaymentInformation paymentInformation) {
        Order order = new Order();
		DeliveryInformation df = new DeliveryInformation();
		df.setAddress(deliveryAddress);  
		order.setDeliveryInformation(df);
		order.setOrderItems(lineItems);
		order.setPaymentInformation(paymentInformation);
        
        Uni<Order> ret = orderRepository.persist(order).onItem().invoke(savedOrder -> {
            OrderEvent orderEvent = OrderEvent.builder().order(savedOrder).type(OrderEventType.CREATED).build();
            publishService.publishOrderEvent(orderEvent);
        });
        return ret;
    }

    @WithTransaction
    public Uni<List<Order>> findAll() {
        return orderRepository.listAll();
    }
}
