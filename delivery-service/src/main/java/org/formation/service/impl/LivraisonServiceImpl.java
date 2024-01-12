package org.formation.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.formation.config.NotificationServiceConfig;
import org.formation.domain.Livraison;
import org.formation.domain.Livreur;
import org.formation.domain.Status;
import org.formation.interceptor.Logged;
import org.formation.service.LivraisonService;
import org.formation.service.NotificationDto;
import org.formation.service.NotificationService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
@Logged
public class LivraisonServiceImpl implements LivraisonService {

	@Inject
	NotificationServiceConfig notificationServiceConfig;

	@RestClient 
    NotificationService notificationService;

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

		notificationService.sendMail(NotificationDto.builder().to("david.thibau@gmail.com").subject("Création Livraison").text(livraison.toString()).build());
		return livraison;
	}
	
	@Override
	public void affect(Long livraisonId, Long livreurId) {
		Livraison livraisonManaged = entityManager.find(Livraison.class, livraisonId);
		if ( livraisonManaged == null ) {
			throw new EntityNotFoundException("Livraison non trouvée id=" + livraisonId);
		}
		Livreur livreur = entityManager.find(Livreur.class, livreurId);
		if ( livreur == null ) {
			throw new EntityNotFoundException("Livreur non trouvé id=" + livreurId);
		}
		livraisonManaged.setLivreur(livreur);
	}

	@Override
	public void start(Long livraisonId) {
		Livraison livraisonManaged = entityManager.find(Livraison.class, livraisonId);
		if ( livraisonManaged == null ) {
			throw new EntityNotFoundException("Livraison non trouvée id=" + livraisonId);
		}
		livraisonManaged.setStatus(Status.EN_COURS);
	}

	@Override
	public void complete(Long livraisonId) {
		Livraison livraisonManaged = entityManager.find(Livraison.class, livraisonId);
		if ( livraisonManaged == null ) {
			throw new EntityNotFoundException("Livraison non trouvée id=" + livraisonId);
		}
		livraisonManaged.setStatus(Status.DISTRIBUE);
	}

	@Override
	public Optional<Livraison> load(Long livraisonId) {
		Livraison livraisonManaged = entityManager.find(Livraison.class, livraisonId);
		if ( livraisonManaged == null ) {
			return Optional.empty();
		}
		return Optional.of(livraisonManaged);
	}

}
