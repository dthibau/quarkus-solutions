package org.formation.web;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.formation.service.TicketService;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/tickets")
public class TicketResource {

	@Inject TicketService ticketService;
	
	@GET
	public List<Ticket> findAll() {
		return Ticket.listAll();
	}
	
	@POST
	public Ticket acceptOrder(@RestQuery Long orderId, List<ProductRequest> productsRequest) {
		return ticketService.createTicket(orderId, productsRequest);
	}
	
	@POST
	@Path("/{ticketId}/pickup")
	public Ticket noteTicketReadyToPickUp(@RestPath String ticketId) {
		return ticketService.readyToPickUp(ticketId);
	}
}
