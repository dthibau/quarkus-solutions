package org.formation.service;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.formation.domain.Address;
import org.formation.domain.DeliveryInformation;
import org.formation.domain.Order;
import org.formation.domain.OrderItem;
import org.formation.domain.OrderRepository;
import org.formation.domain.PaymentInformation;
import org.formation.domain.event.OrderEvent;
import org.formation.domain.event.OrderEventType;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OrderService {

    @RestClient
    @Inject
    NotificationService notificationService;

    @Inject
    PublishService publishService;

    @Inject
    OrderRepository orderRepository;

    @WithTransaction
    public Uni<Order> createOrder(List<OrderItem> lineItems, Address deliveryAddress,
            PaymentInformation paymentInformation) {
        Order order = new Order();
        DeliveryInformation df = new DeliveryInformation();
        df.setAddress(deliveryAddress);
        order.setDeliveryInformation(df);
        order.setOrderItems(lineItems);
        order.setPaymentInformation(paymentInformation);

        return orderRepository.persistAndFlush(order)
                .onItem().invoke(pesistedOrder -> {
                    Log.info("Order created " + pesistedOrder.getId() + " about to notify");
                    NotificationDto dto = NotificationDto.builder().to("david.thibau@gmail.com")
                            .subject("Order created").text(order.toString()).build();
                    notificationService.sendMail(dto).subscribe().with(
                            n -> Log.info("Notification sent " + n),
                            f -> Log.error("Error sending notification " + f));
                })
                .onItem().invoke(pesistedOrder -> {
                    Log.info("Order created " + pesistedOrder.getId() + " about to publish");
                    publishService
                            .publish(OrderEvent.builder().type(OrderEventType.CREATED).order(pesistedOrder).build());
                })
                ;
    }
}
