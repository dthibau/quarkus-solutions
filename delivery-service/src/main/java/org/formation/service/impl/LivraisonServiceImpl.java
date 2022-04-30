package org.formation.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.formation.config.NotificationServiceConfig;
import org.formation.domain.Livraison;
import org.formation.domain.Livreur;
import org.formation.domain.Status;
import org.formation.interceptor.Logged;
import org.formation.service.LivraisonService;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
@Logged
public class LivraisonServiceImpl implements LivraisonService {

	@Inject
	NotificationServiceConfig notificationServiceConfig;
	
	List<Livraison> livraisons;
	
	@ConfigProperty(name = "quarkus.http.port") 
	String port;
	
	@PostConstruct
	public void init() {
		livraisons = new ArrayList<>();
		livraisons.add(Livraison.builder().id(1).noCommande("1").creationDate(Instant.now()).status(Status.DISTRIBUE).build());
		livraisons.add(Livraison.builder().id(1).noCommande("2").creationDate(Instant.now()).status(Status.DISTRIBUE).build());
		livraisons.add(Livraison.builder().id(1).noCommande("3").creationDate(Instant.now()).status(Status.DISTRIBUE).build());
		livraisons.add(Livraison.builder().id(1).noCommande("4").creationDate(Instant.now()).status(Status.DISTRIBUE).build());		
	}
	@Override
	public Multi<Livraison> findAll() {
		System.out.println("Notificaiton Service Config " + notificationServiceConfig);
		return Multi.createFrom().items(livraisons.stream()); 
	}

	@Override
	public Optional<Livraison> load(Livraison livraison) {
		int index = livraisons.indexOf(livraison);
		return index == -1 ? Optional.empty() : Optional.of(livraisons.get(index));
	}
		
	@Override
	public Livraison create(String noCommande) {
		Livraison livraison = Livraison.builder().id(livraisons.size()+1).noCommande(noCommande).creationDate(Instant.now()).status(Status.CREE).build();
		livraisons.add(livraison);
		return livraison;
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
