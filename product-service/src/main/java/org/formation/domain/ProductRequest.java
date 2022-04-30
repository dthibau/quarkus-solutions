package org.formation.domain;

import lombok.Data;


@Data
public class ProductRequest {

	private Long id;
	
	private String reference;
	private int quantity;
}
