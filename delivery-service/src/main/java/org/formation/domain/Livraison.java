package org.formation.domain;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Livraison {

	private long id;
	
	private String noCommande;
	
	private Livreur livreur;
	
	private Status status;
	

	private Instant creationDate;
	
	


}
