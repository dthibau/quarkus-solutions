package org.formation.domain;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class PaymentInformation {
	
	@Length(min = 16, max = 40)
	private String paymentToken;
}
