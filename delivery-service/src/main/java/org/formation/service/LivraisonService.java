package org.formation.service;

import java.util.List;
import java.util.Optional;

import org.formation.domain.Livraison;
import org.formation.domain.Livreur;

public interface LivraisonService {

	public Optional<Livraison> load(long id);

	public List<Livraison> findAll();
	
	public Livraison create(String noCommande);
	
	public void affect(Long idLivraison, Long idLivreur);
	
	public void start(Long idLivraison);
	
	public void complete(Livraison livraison);
	
}
