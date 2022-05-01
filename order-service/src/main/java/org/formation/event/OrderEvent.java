package org.formation.event;

import org.formation.domain.Order;
import org.formation.domain.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.Data;

@Data
@AllArgsConstructor
public class OrderEvent {

	OrderStatus orderStatus;
	Order order;
}
