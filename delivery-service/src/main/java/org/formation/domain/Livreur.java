package org.formation.domain;

import java.util.List;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Livreur {

	private long id;
	
	private String nom;
	
	private String telephone;

	private List<Review> reviews;
}
