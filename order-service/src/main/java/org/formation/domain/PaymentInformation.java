package org.formation.domain;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PaymentInformation {
	
	@Length(min = 16, max = 40)
	private String paymentToken;
}
