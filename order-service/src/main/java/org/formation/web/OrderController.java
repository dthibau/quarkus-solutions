package org.formation.web;

import java.util.List;

import org.formation.domain.Order;
import org.formation.domain.OrderRepository;
import org.formation.service.OrderService;
import org.jboss.resteasy.reactive.ResponseStatus;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/orders")
public class OrderController {
    
    @Inject
    OrderRepository orderRepository;

    @Inject
    OrderService orderService;
    
    @GET
    public Uni<List<Order>> getOrders() {
        return orderRepository.findAll().list();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    public Uni<Order> createOrder(@Valid OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest.getLineItems(), orderRequest.getDeliveryAddress(), orderRequest.getPaymentInformation());
    }
}
