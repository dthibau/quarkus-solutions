package org.formation.event;

import org.formation.domain.Order;

import lombok.Data;

@Data
public class OrderEvent {

	OrderStatus orderStatus;
	Order order;
	
	
}
