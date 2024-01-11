package org.formation.domain;

import lombok.Data;

@Data
public class Produit {

	private long id;
	
	private String reference;
	
	private String nom;
	
	private String description;
	
	private Float prixUnitaire;
	
	private Integer availability;
	
	private Dimension dimension;
	
	private Fournisseur fournisseur;
	
}
