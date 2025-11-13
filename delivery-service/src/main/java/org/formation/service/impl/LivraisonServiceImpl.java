package org.formation.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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
        return entityManager.createQuery("from Livraison ", Livraison.class).getResultList();
    }

    @Override
    public Livraison create(String noCommande) {
        Livraison livraison = Livraison.builder()
                .noCommande(noCommande)
                .creationDate(Instant.now())
                .status(Status.CREE)
                .build();

        entityManager.persist(livraison);
        return livraison;
    }

    @Override
    public void affect(Livraison livraison, Livreur livreur) {
        livraison = entityManager.find(Livraison.class, livraison.getId());
        // livreur = entityManager.find(Livreur.class, livreur.getId());
        livraison.setLivreur(livreur);
        livraison.setStatus(Status.LIVREUR_AFFECTE);
    }

    @Override
    public void start(Livraison livraison) {
        livraison.status = Status.EN_COURS;
        entityManager.merge(livraison);
    }

    @Override
    public void complete(Livraison livraison) {
        livraison = entityManager.merge(livraison);
        livraison.status = Status.DISTRIBUE;
    }

}
