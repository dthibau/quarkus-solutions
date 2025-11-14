package org.formation.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Embeddable
public class PaymentInformation {


	@Length(min = 16, max = 40)
	private String paymentToken;
}
