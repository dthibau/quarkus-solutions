package org.formation.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.formation.domain.TicketStatus;
import org.formation.event.OrderEvent;
import org.formation.event.OrderStatus;

import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class TicketService {

	@Inject
	@Channel("order")
	Multi<OrderEvent> orderEvents;

	@Channel("order-out")
    Emitter<OrderEvent> orderEventEmitter;
	

	void onStart(@Observes StartupEvent ev) {
		Log.info("Subscribing ...");
		orderEvents.subscribe().with(e -> receiveOrderEvent(e));
	}

	public Uni<Void> receiveOrderEvent(OrderEvent orderEvent) {
		Log.info("Receiving message " + orderEvent);
		if (orderEvent.getOrderStatus().equals(OrderStatus.CREATED)) {
			createTicket(orderEvent.getOrder().getId(), orderEvent.getOrder().getProductRequests());
			orderEvent.setOrderStatus(OrderStatus.PENDING);
			orderEventEmitter.send(orderEvent);
		}
		return Uni.createFrom().voidItem();
	}

	public Ticket createTicket(Long orderId, List<ProductRequest> productsRequest) {
		Ticket t = new Ticket();
		t.setOrderId("" + orderId);
		t.setProductRequests(productsRequest);
		t.setStatus(TicketStatus.CREATED);

		Ticket.persist(t);

		Log.info("Creating ticket " + t);

		return t;
	}

	public Ticket readyToPickUp(String ticketId) {

		Ticket t = Ticket.findById(new ObjectId(ticketId));

		t.setStatus(TicketStatus.READY_TO_PICK);

		Ticket.persistOrUpdate(t);

		return t;

	}
}
