package org.formation.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.formation.config.NotificationServiceConfig;
import org.formation.domain.Livraison;
import org.formation.domain.Livreur;
import org.formation.domain.Status;
import org.formation.interceptor.Logged;
import org.formation.service.LivraisonService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.vertx.http.runtime.devmode.Json;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.java.Log;

@ApplicationScoped
@Logged
@Log
public class LivraisonServiceImpl implements LivraisonService {

	@ConfigProperty(name = "quarkus.http.port") 
	String port;
	@Inject
	NotificationServiceConfig notificationServiceConfig;


	List<Livraison> livraisons;
	
	@PostConstruct
	public void init() throws JsonProcessingException {
		log.info("Listening to port :  " + port);
		log.info("Notification service config : " + notificationServiceConfig.completeUrl());
		
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
