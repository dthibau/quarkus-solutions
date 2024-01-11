package org.formation.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.logging.Log;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@QuarkusTest
@TestTransaction
public class LivreurEntityTest {
    
    @Inject
    EntityManager entityManager;
    
    @BeforeEach
    public void initializeSequence() {
        String sql = "ALTER SEQUENCE livreur_seq RESTART WITH 1000"; // Remplacez "ma_entite_id_seq" par le nom réel de votre séquence
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Test
    public void testCreate() {
        Long initialSize = Livreur.count();
        Log.info(initialSize.toString());
        Livreur toto = Livreur.builder().nom("Toto").status(StatusLivreur.ACTIF).build();
        toto.persist();

        entityManager.flush(); 
        entityManager.clear();

        assertAll("Vérification création", 
                () -> assertNotNull(toto),
                () -> assertNotNull(toto.id),
                () -> assertNotNull(toto.nom),
                () -> assertEquals(initialSize+1, Livreur.count()));
    }

    @Test
    public void testFindActifs() {
        int initialSize = Livreur.findActifLivreurs().size();

        Livreur actif = Livreur.builder().nom("Toto").status(StatusLivreur.ACTIF).build();
        actif.persist();
        Livreur repos = Livreur.builder().nom("Titi").status(StatusLivreur.REPOS).build();
        repos.persist();

        entityManager.flush(); 
        entityManager.clear();

        assertEquals(initialSize+1, Livreur.findActifLivreurs().size());
    }

    @Test
    public void testAddThenEraseReview() {

        Livreur speedy = Livreur.findById(1);
        
        speedy.addReview(Review.builder().commentaire("Trop rapide !").note(5).build());

        entityManager.flush();
        entityManager.clear();

        speedy = Livreur.findById(1);

        // Assertion sur add
        assertEquals(1, speedy.reviews.size());

        speedy.resetReviews();

        entityManager.flush();
        entityManager.clear();

// Assertion sur reset
        assertEquals(0, speedy.reviews.size());
    }


}
