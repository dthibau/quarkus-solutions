package org.formation.service;

import java.util.List;
import java.util.Optional;

import org.formation.domain.Livraison;
import org.formation.domain.Livreur;

public interface LivraisonService {

	public List<Livraison> findAll();

	public Optional<Livraison> load(Long id);
	
	public Livraison create(String noCommande);
	
	public void affect(Long idLivraison, Long idLivreur);
	
	public void start(Long id);
	
	public void complete(Long id);
	
}
