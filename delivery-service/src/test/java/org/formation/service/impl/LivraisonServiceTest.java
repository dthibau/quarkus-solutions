package org.formation.service.impl;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.formation.service.LivraisonService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class LivraisonServiceTest {

    @Inject
    LivraisonService livraisonService;


    @Test
    void testFindAll() {

        assertEquals(4,livraisonService.findAll().size());

    }
}
