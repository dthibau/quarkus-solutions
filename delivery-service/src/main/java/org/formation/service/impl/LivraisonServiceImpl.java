package org.formation.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
import org.hibernate.Hibernate;


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
    public Optional<Livraison> load(long id) {
        Livraison livraison = entityManager.find(Livraison.class, id);
        if ( livraison == null )
            return Optional.empty();
        if ( livraison.livreur != null) {
            Hibernate.initialize(livraison.livreur.reviews);
        }
         return Optional.of(livraison);
    }

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
    public void affect(Long idLivraison, Long idLivreur) {
        Livraison livraison = entityManager.find(Livraison.class, idLivraison);
        // livreur = entityManager.find(Livreur.class, livreur.getId());
        livraison.setLivreur(entityManager.find(Livreur.class, idLivreur));
        livraison.setStatus(Status.LIVREUR_AFFECTE);
    }

    @Override
    public void start(Long idLivraison) {
        Livraison livraison = entityManager.find(Livraison.class, idLivraison);
        livraison.status = Status.EN_COURS;
    }

    @Override
    public void complete(Livraison livraison) {
        livraison = entityManager.merge(livraison);
        livraison.status = Status.DISTRIBUE;
    }

}
