package org.formation.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DeliveryInformation {

	private LocalDateTime deliveryTime;
	
	private Address address;
}
