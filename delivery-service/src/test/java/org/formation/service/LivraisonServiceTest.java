package org.formation.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.formation.domain.Livraison;
import org.formation.domain.Status;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestTransaction
public class LivraisonServiceTest {
    
    @Inject
    LivraisonService livraisonService;

    @Inject
    EntityManager entityManager;
    
    @Test 
    public void testCreate() {
        int initialSize = livraisonService.findAll().size();
        Livraison livraison = livraisonService.create("1234");

        entityManager.clear();

        assertAll("Vérification de la livraison après création", 
                () -> assertNotNull(livraison),
                () -> assertNotNull(livraison.getId()),
                () -> assertEquals("1234", livraison.getNoCommande()),
                () -> assertEquals(Status.CREE, livraison.getStatus()),
                () -> assertEquals(initialSize+1, livraisonService.findAll().size()));
    }

    @Test
    public void testStart() {
        Livraison livraison = livraisonService.create("1234");

        entityManager.clear();

        livraisonService.start(livraison);

        entityManager.clear();

        assertEquals(Status.CREE, livraisonService.findAll().get(0).getStatus());
    }
}
