package org.formation.service;

import java.util.List;

import org.formation.domain.Livraison;
import org.formation.domain.Livreur;

public interface LivraisonService {

	public List<Livraison> findAll();
	
	public void create(String noCommande);
	
	public void affect(Livraison livraison, Livreur livreur);
	
	public void start(Livraison livraison);
	
	public void complete(Livraison livraison);
	
}
