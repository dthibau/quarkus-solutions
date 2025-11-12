package org.formation.web;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.formation.service.LivraisonService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class LivraisonControllerTest {

    @InjectMock
    LivraisonService livraisonService;

    @TestHTTPEndpoint(LivraisonController.class)
    @TestHTTPResource
    URL endpoint;

    @Test
    void whenFindAll_then200Ok() {

        Mockito.when(livraisonService.findAll()).thenReturn(java.util.Collections.emptyList());

        given()
          .when().get(endpoint)
          .then()
             .statusCode(200)
                .body("$", hasSize(0));

        Mockito.verify(livraisonService).findAll();

    }

}
