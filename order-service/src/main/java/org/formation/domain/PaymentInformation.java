package org.formation.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInformation {

	@Length(min = 16, max = 40)
	private String paymentToken;
}
