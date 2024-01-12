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

@ApplicationScoped
public class OrderService {
    
    @Inject
    OrderRepository orderRepository;

    @WithTransaction
    public Uni<Order> createOrder(List<OrderItem> lineItems, Address deliveryAddress, PaymentInformation paymentInformation) {
        Order order = new Order();
		DeliveryInformation df = new DeliveryInformation();
		df.setAddress(deliveryAddress);  
		order.setDeliveryInformation(df);
		order.setOrderItems(lineItems);
		 order.setPaymentInformation(paymentInformation);
        
        return orderRepository.persist(order);
    }
}
