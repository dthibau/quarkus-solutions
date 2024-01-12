package org.formation.service;

import java.util.List;
import java.util.Optional;

import org.formation.domain.Livraison;
import org.formation.domain.Livreur;

public interface LivraisonService {

	public List<Livraison> findAll();
	
	public Optional<Livraison> load(Long livraisonId);
	
	public Livraison create(String noCommande);
	
	public void affect(Long livraisonId, Long livreurId);
	
	public void start(Long livraisonId);
	
	public void complete(Long livraisonId);
	
}
