package org.formation.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Embeddable
public class PaymentInformation {

	@Pattern(regexp = "^\\d{13,19}$", message = "Numéro de carte invalide")
	private String paymentToken;
}
