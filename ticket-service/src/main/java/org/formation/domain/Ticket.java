package org.formation.domain;

import java.util.List;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Ticket extends PanacheMongoEntity {

	Long orderId;
	
	private TicketStatus status;
	
	List<ProductRequest> productRequests;
	
	
}
