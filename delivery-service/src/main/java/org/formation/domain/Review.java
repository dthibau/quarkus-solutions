package org.formation.domain;

import lombok.Data;

@Data
public class Review {

	private long id;
	
	private int note;
	
	private String commentaire;
}
