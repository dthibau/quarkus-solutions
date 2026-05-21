package org.formation.web;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.formation.domain.Event;
import org.formation.domain.OrderRepository;
import org.formation.domain.Order;
import org.formation.service.EnrichedOrder;
import org.formation.service.OrderService;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import java.net.URI;
import java.util.List;

@Path("/orders")
public class OrderResource {

    @Inject
    OrderRepository orderRepository;

    @Inject
    OrderService orderService;

    @Channel("orders-event")
	Multi<Event> orderEvents;

    @GET
	@Path("/stream")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public Multi<Event> stream() {
           return orderEvents;
    }

    @GET
    public Uni<List<Order>> getOrders() {
        return orderRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Uni<EnrichedOrder> getOrder(@RestPath long id) {
        return orderService.findById(id);
    }

    @POST
    public Uni<RestResponse<Void>> createOrder(@Valid OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest.lineItems, orderRequest.deliveryAddress, orderRequest.paymentInformation)
                .onItem().transform(order -> RestResponse.created(URI.create("/orders/" + order.getId())));
    }
}
