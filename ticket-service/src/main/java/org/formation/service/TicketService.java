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


		Log.info("Creating ticket for " + orderId + " with " + productsRequest.size() + " products");

		return null;
	}

	public Ticket readyToPickUp(String ticketId) {



		return null;

	}
}
