package org.formation.domain;

import java.time.LocalDateTime;

import javax.persistence.Embedded;

import lombok.Data;

@Data
public class DeliveryInformation {

	private LocalDateTime deliveryTime;
	
	@Embedded
	private Address address;
}
