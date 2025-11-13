package org.formation.domain;

import io.quarkus.arc.All;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	private String rue;
	private String ville;
	private String codePostal;

}