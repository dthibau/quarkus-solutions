package org.formation.service;

import java.util.List;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.formation.domain.Order;
import org.formation.domain.OrderRequest;

import io.quarkus.oidc.token.propagation.reactive.AccessTokenRequestReactiveFilter;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;


@Path("/orders")
@RegisterRestClient(configKey = "orders-api")
@RegisterProvider(AccessTokenRequestReactiveFilter.class)
public interface OrderService {
    
    @GET
    public Uni<List<Order>> findAll();

    @POST
    public Uni<Order> createOrder(OrderRequest orderRequest);
}
