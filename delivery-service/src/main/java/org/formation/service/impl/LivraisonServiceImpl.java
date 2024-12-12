package org.formation.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.formation.config.NotificationServiceConfig;
import org.formation.domain.Livraison;
import org.formation.domain.Livreur;
import org.formation.domain.Status;
import org.formation.interceptor.Logged;
import org.formation.service.LivraisonService;
import java.time.Instant;
import java.util.List;
import jakarta.persistence.EntityManager;


@ApplicationScoped
@Logged
@Log
@Transactional
public class LivraisonServiceImpl implements LivraisonService {

    @ConfigProperty(name = "quarkus.http.port")
    String port;
    @Inject
    NotificationServiceConfig notificationServiceConfig;

	@Inject
	EntityManager entityManager;


	@Override
	public List<Livraison> findAll() {
		return entityManager.createQuery("from Livraison", Livraison.class).getResultList();
	}

	@Override
	public Livraison create(String noCommande) {
		Livraison livraison = Livraison.builder().noCommande(noCommande).creationDate(Instant.now()).status(Status.CREE).build();
		entityManager.persist(livraison);
		return livraison;
	}

	@Override
	public void affect(Livraison livraison, Livreur livreur) {
		entityManager.find(Livraison.class, livraison.getId()).setLivreur(livreur);
	}

	@Override
	public void start(Livraison livraison) {
		entityManager.find(Livraison.class, livraison.getId()).setStatus(Status.EN_COURS);
	}

	@Override
	public void complete(Livraison livraison) {
		entityManager.find(Livraison.class, livraison.getId()).setStatus(Status.DISTRIBUE);
	}

}
