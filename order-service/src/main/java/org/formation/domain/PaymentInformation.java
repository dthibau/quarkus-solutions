package org.formation.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PaymentInformation {
	
	private String paymentToken;
}
