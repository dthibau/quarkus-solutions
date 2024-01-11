package org.formation.domain;

import java.util.List;

import lombok.Data;

@Data
public class Ticket  {

	String orderId;
	
	private TicketStatus status;
	
	List<ProductRequest> productRequests;
	
	
}
