package org.formation.web;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.formation.domain.Order;
import org.formation.service.OrderService;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;

@Path("/orders")
@RequiredArgsConstructor
public class OrderResource {

    private final OrderService orderService;

    @GET
    public Uni<List<Order>> getOrders() {
        return orderService.findAll();
    }

    @POST
    @ResponseStatus(201)
    public Uni<Order> createOrder(OrderRequest orderRequest) {
        return orderService.createOrder(
                orderRequest.getLineItems(),
                orderRequest.getDeliveryAddress(),
                orderRequest.getPaymentInformation()
        );
    }

}
