package org.formation.service;

import org.formation.domain.Livraison;
import org.formation.domain.Livreur;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface LivraisonService {

	public Multi<Livraison> findAll();
	
//	public List<Livraison> findAllSync();
	
	public Uni<Livraison> load(Livraison livraison);
	
	public Uni<Livraison> create(String noCommande);
	
	public Uni<Livraison> affect(Livraison livraison, Livreur livreur);
	
	public Uni<Livraison> start(Livraison livraison);
	
	public Uni<Livraison> complete(Livraison livraison);
	
}
