package org.formation.service;

import java.util.List;


import org.bson.types.ObjectId;
import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.formation.domain.TicketStatus;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TicketService {


	public Ticket createTicket(Long orderId, List<ProductRequest> productsRequest) {

		Ticket ticket = new Ticket();
		ticket.setOrderId(orderId.toString());
		ticket.setStatus(TicketStatus.CREATED);
		ticket.setProductRequests(productsRequest);

		ticket.persist();
		Log.info("Creating ticket for " + orderId + " with " + productsRequest.size() + " products");

		return ticket;
	}

	public Ticket readyToPickUp(String ticketId) {

		Ticket ticket = Ticket.findById(new ObjectId(ticketId));
		ticket.setStatus(TicketStatus.READY_TO_PICK);
		ticket.persistOrUpdate();

		return ticket;

	}
}
