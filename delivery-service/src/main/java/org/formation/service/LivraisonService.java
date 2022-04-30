package org.formation.service;

import java.util.Optional;

import org.formation.domain.Livraison;
import org.formation.domain.Livreur;

import io.smallrye.mutiny.Multi;

public interface LivraisonService {

	public Multi<Livraison> findAll();
	
	public Optional<Livraison> load(Livraison livraison);
	
	public Livraison create(String noCommande);
	
	public void affect(Livraison livraison, Livreur livreur);
	
	public void start(Livraison livraison);
	
	public void complete(Livraison livraison);
	
}
