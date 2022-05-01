package org.formation.service;

import org.formation.domain.OrderItem;

import lombok.Data;

@Data
public class ProductRequest {

	private String reference;
	private int quantity;
	
	public ProductRequest(OrderItem orderItem) {
		this.reference = orderItem.getRefProduct();
		this.quantity = orderItem.getQuantity();
	}
}
