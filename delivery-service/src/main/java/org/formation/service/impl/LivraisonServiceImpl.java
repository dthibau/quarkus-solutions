package org.formation.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
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
    public Livraison create(String noCommande) {
        Livraison livraison = Livraison.builder().noCommande(noCommande).creationDate(Instant.now()).status(Status.CREE).build();
        entityManager.persist(livraison);
        return livraison;
    }

    @Override
    public void affect(Livraison livraison, Livreur livreur) {
        livraison.setLivreur(livreur);
        entityManager.merge(livraison);

    }

    @Override
    public void start(Livraison livraison) {
        livraison.setStatus(Status.EN_COURS);
        entityManager.merge(livraison);
    }

    @Override
    public void complete(Livraison livraison) {
        livraison.setStatus(Status.DISTRIBUE);
        entityManager.merge(livraison);

    }

}
