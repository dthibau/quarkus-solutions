package org.formation.domain;

import java.util.List;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Data;

@Data
public class Ticket extends PanacheMongoEntity {

	String orderId;
	
	private TicketStatus status;
	
	List<ProductRequest> productRequests;
	
	
}
