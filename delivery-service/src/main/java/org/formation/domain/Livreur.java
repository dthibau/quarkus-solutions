package org.formation.domain;

import java.util.List;

import lombok.Data;


@Data
public class Livreur {

	private long id;
	
	private String nom;
	
	private String telephone;

	private List<Review> reviews;
}
