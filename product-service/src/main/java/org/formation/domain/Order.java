package org.formation.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class Order {

	private long id;
	
	private Instant date;
	
	List<ProductRequest> productRequests = new ArrayList<>();
	
}
