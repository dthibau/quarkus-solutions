package org.formation;

import io.quarkus.logging.Log;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.net.URL;

import org.formation.domain.Livraison;
import org.formation.web.LivraisonController;

@QuarkusTest
@TestHTTPEndpoint(LivraisonController.class)
class LivraisonResourceTest {

	@TestHTTPResource("")
	URL livraisonsUrl;




    @Test
    void whenGetLivraisonsCheckStatusAndSizeofArray() {
        
        given()
          .when().get(livraisonsUrl.getPath())
          .then()
             .statusCode(200)
             .body("$", hasSize(4))
             .body("[0].id", equalTo(1));

    }
    @Test
    void whenCreateLivraisonsCheckStatusAndId() {
        
        given()
          .when().post(livraisonsUrl.getPath()+"?noCommande=1234")
          .then()
             .statusCode(201)
             .body("id", equalTo(5))
             .body("noCommande", equalTo("1234"))
             .body("status", equalTo("CREE"));
        

    }
}