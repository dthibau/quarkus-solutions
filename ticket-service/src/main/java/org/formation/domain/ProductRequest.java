package org.formation.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

	private Long id;
	
	@JsonAlias({ "refProduct", "reference" })
	private String reference;
	private int quantity;
}
