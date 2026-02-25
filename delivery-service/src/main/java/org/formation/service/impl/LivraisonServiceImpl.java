package org.formation.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.formation.config.NotificationServiceConfig;
import org.formation.domain.Livraison;
import org.formation.domain.Livreur;
import org.formation.domain.Status;
import org.formation.interceptor.Logged;
import org.formation.service.LivraisonService;


@ApplicationScoped
@Logged
@Log
@RequiredArgsConstructor
public class LivraisonServiceImpl implements LivraisonService {

    @ConfigProperty(name = "quarkus.http.port")
    String port;
    private final  NotificationServiceConfig notificationServiceConfig;
    private final EntityManager entityManager;


    @Override
    public List<Livraison> findAll() {
        log.fine("Listening to port : " + port);
        log.fine("Notification service config : " + notificationServiceConfig.completeUrl());
        return entityManager.createQuery("from Livraison").getResultList();
    }

    @Override
    public Optional<Livraison> load(Long id) {
        return entityManager.find(Livraison.class, id) != null ?
                      Optional.of(entityManager.find(Livraison.class, id)) : Optional.empty();
    }

    @Override
    public Livraison create(String noCommande) {
        Livraison livraison = Livraison.builder().noCommande(noCommande).creationDate(Instant.now()).status(Status.CREE).build();
        entityManager.persist(livraison);
        return livraison;
    }

    @Override
    public void affect(Long idLivraison, Long idLivreur) {
        Livraison livraison = entityManager.find(Livraison.class,idLivraison);
        Livreur livreur = entityManager.find(Livreur.class,idLivreur);
        livraison.setLivreur(livreur);
        entityManager.merge(livraison);

    }

    @Override
    public void start(Long id) {
        Livraison livraison = entityManager.find(Livraison.class,id);
        if ( livraison == null ) {
            throw new EntityNotFoundException("No such livraison "+id);
        }
        livraison.setStatus(Status.EN_COURS);
    }

    @Override
    public void complete(Long id) {
        Livraison livraison = entityManager.find(Livraison.class,id);
        if ( livraison == null ) {
            throw new EntityNotFoundException("No such livraison "+id);
        }
        livraison.setStatus(Status.DISTRIBUE);

    }

}
