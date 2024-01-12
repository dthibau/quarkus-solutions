package org.formation.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class LivraisonServiceImplTest {

    @Inject
    LivraisonService livraisonService;

    @Test
    void testFindAll() {
        Assertions.assertEquals(4, livraisonService.findAll().size());
    }
}
