package org.formation.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.formation.domain.Livraison;
import org.formation.domain.Livreur;
import org.formation.domain.Status;
import org.formation.interceptor.Logged;
import org.formation.service.LivraisonService;

@ApplicationScoped
@Logged
public class LivraisonServiceImpl implements LivraisonService {

	List<Livraison> livraisons;
	
	@PostConstruct
	public void init() {
		livraisons = new ArrayList<>();
		livraisons.add(Livraison.builder().id(1).noCommande("1").creationDate(Instant.now()).status(Status.DISTRIBUE).build());
		livraisons.add(Livraison.builder().id(1).noCommande("2").creationDate(Instant.now()).status(Status.DISTRIBUE).build());
		livraisons.add(Livraison.builder().id(1).noCommande("3").creationDate(Instant.now()).status(Status.DISTRIBUE).build());
		livraisons.add(Livraison.builder().id(1).noCommande("4").creationDate(Instant.now()).status(Status.DISTRIBUE).build());		
	}
	@Override
	public List<Livraison> findAll() {
		return livraisons;
	}

	@Override
	public void create(String noCommande) {
		livraisons.add(Livraison.builder().id(livraisons.size()+1).noCommande(noCommande).creationDate(Instant.now()).status(Status.CREE).build());
		
	}

	@Override
	public void affect(Livraison livraison, Livreur livreur) {
		int index = livraisons.indexOf(livraison);
		livraisons.get(index).setLivreur(livreur);
		
	}

	@Override
	public void start(Livraison livraison) {
		int index = livraisons.indexOf(livraison);
		livraisons.get(index).setStatus(Status.EN_COURS);
		
	}

	@Override
	public void complete(Livraison livraison) {
		int index = livraisons.indexOf(livraison);
		livraisons.get(index).setStatus(Status.DISTRIBUE);
		
		
	}

}
