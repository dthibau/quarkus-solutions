package org.formation.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class LivraisonServiceTest {

    @Inject
    LivraisonService livraisonService;

    @Test
    public void testFindAll() {
        var livraisons = livraisonService.findAll();

        assertAll("Vérifications findAll",
                () -> assertNotNull(livraisons),
                () -> assertEquals(4, livraisons.size()));

    }
}
