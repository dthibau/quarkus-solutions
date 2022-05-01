package org.formation.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.formation.domain.Order;
import org.formation.domain.OrderRepository;
import org.formation.domain.OrderStatus;
import org.formation.event.OrderEvent;
import org.formation.web.CreateOrderRequest;

import lombok.extern.java.Log;

@ApplicationScoped
@Log
public class OrderService {

	
	@Inject
	OrderRepository orderRepository;
	
	
	public Order createOrder(CreateOrderRequest createOrderRequest) {
		Order order = createOrderRequest.getOrder();
		// Save in local DataBase
		orderRepository.persist(order);
		
		// Produce event to Kafka
		publishEvent(new OrderEvent(OrderStatus.PENDING,order));
		
		
		log.info("Ticket created ");
		
		return order;
	}
	
	/**
	 * Produce Event to kafka topic "order"
	 * @param orderEvent
	 */
	public void publishEvent(OrderEvent orderEvent) {
		
	}
	
}
