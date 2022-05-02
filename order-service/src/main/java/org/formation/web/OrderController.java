package org.formation.web;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.formation.domain.Order;
import org.formation.domain.OrderRepository;
import org.formation.event.OrderEvent;
import org.formation.event.OrderStatus;
import org.formation.service.OrderService;
import org.jboss.resteasy.reactive.ResponseStatus;

import io.smallrye.mutiny.Multi;

@Path("/orders")
public class OrderController {

	@Inject
	OrderService orderService;
	
	@Channel("order")
	Multi<OrderEvent> orderEvents;
	
	@Inject
	OrderRepository orderRepository;
	
	@POST
	@ResponseStatus(201)
	public Order createOrder(CreateOrderRequest request) {
		
		Order ret = orderService.createOrder(request);
		
		// Produce event to Kafka
		orderService.publishEvent(new OrderEvent(OrderStatus.CREATED,ret));
		
		return ret;
		
	}
	
	@GET
	public List<Order> findAll() {
		return orderRepository.findAll().list();
	}
	
	@GET
	@Path("/status")
	@Produces(MediaType.SERVER_SENT_EVENTS) 
	public Multi<OrderEvent> stream() {
	    return orderEvents; 
	}
}
