package org.formation.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	private long id;
	
	private Instant date;
	
	private float discount;
	

	private PaymentInformation paymentInformation;
	
	private DeliveryInformation deliveryInformation;
	
	List<OrderItem> orderItems = new ArrayList<>();
	
}
