package org.formation.domain;

import io.quarkus.logging.Log;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestTransaction
public class LivreurEntityTest {

    @Inject
    EntityManager entityManager;

    @Test
    public void testCreate() {
        Long initialSize = Livreur.count();
        Log.info(initialSize.toString());
        Livreur toto = Livreur.builder().nom("Toto").status(StatusLivreur.ACTIF).build();
        toto.persist();

        entityManager.flush();
        entityManager.clear();

        Livreur actual = Livreur.findById(toto.id);
        assertAll("Vérification création",
                () -> assertNotNull(actual),
                () -> assertEquals(toto.id,actual.id),
                () -> assertEquals(toto.nom,actual.nom),
                () -> assertEquals(initialSize+1, Livreur.count()));
    }

    @Test
    public void testFindActifs() {
        int initialSize = Livreur.findActifs().size();

        Livreur actif = Livreur.builder().nom("Toto").status(StatusLivreur.ACTIF).build();
        actif.persist();
        Livreur repos = Livreur.builder().nom("Titi").status(StatusLivreur.REPOS).build();
        repos.persist();

        entityManager.flush();
        entityManager.clear();

        assertEquals(initialSize+1, Livreur.findActifs().size());
    }

    @Test
    public void testAddThenEraseReview() {

        Livreur speedy = Livreur.builder().nom("Speedy Gonzales").status(StatusLivreur.ACTIF).build();
        speedy.addReview(Review.builder().commentaire("Trop rapide !").note(5).build());

        entityManager.flush();
        entityManager.clear();

        speedy = Livreur.findById(speedy.id);

        // Assertion sur add
        assertEquals(1, speedy.reviews.size());

        speedy.resetReviews();

        entityManager.flush();
        entityManager.clear();

// Assertion sur reset
        speedy = Livreur.findById(speedy.id);
        assertEquals(0, speedy.reviews.size());
    }


    @Test
    void testAddReview() {

    }
}