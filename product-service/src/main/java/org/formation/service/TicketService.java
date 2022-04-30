package org.formation.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.bson.types.ObjectId;
import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.formation.domain.TicketStatus;

@ApplicationScoped
public class TicketService {

	
	public Ticket createTicket(Long orderId, List<ProductRequest> productsRequest) {
		Ticket t = new Ticket();
		t.setOrderId(""+orderId);
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
