package org.formation.web;


import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.formation.domain.Livraison;
import org.formation.domain.Status;
import org.formation.service.LivraisonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class LivraisonControllerTest {

    @TestHTTPEndpoint(LivraisonController.class)
    @TestHTTPResource("")
    URL url;

    @InjectMock
    LivraisonService livraisonService;

    @BeforeEach
    void setUp() {
        List<Livraison> dummyList = new ArrayList<>();
        dummyList.add(Livraison.builder().id(1).noCommande("1").creationDate(Instant.now()).status(Status.DISTRIBUE).build());
        Mockito.when(livraisonService.findAll()).thenReturn(dummyList);
    }

    @Test
    void testFindAll() {

        given()
                .when().get(url)
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)))
                // Vérifie qu'au moins un élément a ce numéro de session
                .body("id", hasItem(1))
                // Vérifie un champ spécifique du premier élément [0]
                .body("[0].status", equalTo("DISTRIBUE"));;
    }
}
