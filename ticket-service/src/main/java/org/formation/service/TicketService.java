package org.formation.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.formation.domain.TicketStatus;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TicketService {



	public Ticket createTicket(Long orderId, List<ProductRequest> productsRequest) {

		Log.info("Creating ticket for " + orderId + " with " + productsRequest.size() + " products");

		Ticket t = new Ticket();
		t.setOrderId(orderId);
		t.setProductRequests(productsRequest);
		t.setStatus(TicketStatus.CREATED);
		
		Ticket.persist(t);
		
		return t;
	}

	public Ticket readyToPickUp(String ticketId) {

		Ticket t = Ticket.findById(new ObjectId(ticketId));

		t.setStatus(TicketStatus.READY_TO_PICK);
		
		Ticket.persistOrUpdate(t);
		
		return t;
		
	}
}
