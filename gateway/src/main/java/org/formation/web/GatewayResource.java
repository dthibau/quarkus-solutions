package org.formation.web;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.formation.domain.Order;
import org.formation.domain.OrderRequest;
import org.formation.service.OrderService;
import org.jboss.resteasy.reactive.ResponseStatus;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/")
public class GatewayResource {
    

    @RestClient
    @Inject
    OrderService orderService;
    

    @GET
    @Path("/orders")
    public Uni<List<Order>> findAll() {
         return orderService.findAll();
    }

    @POST
    @Path("/orders")
    @ResponseStatus(201)
    public Uni<Order> createOrder(OrderRequest orderRequest) {
         return orderService.createOrder(orderRequest);
    }
}
