package org.formation.web;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.formation.domain.Order;
import org.formation.service.OrderService;
import org.jboss.resteasy.reactive.ResponseStatus;

@Path("/orders")
public class OrderController {

	@Inject
	OrderService orderService;
	
	
	@POST
	@ResponseStatus(201)
	public Order createOrder(CreateOrderRequest request) {
		
		return orderService.createOrder(request);
		
	}
}
