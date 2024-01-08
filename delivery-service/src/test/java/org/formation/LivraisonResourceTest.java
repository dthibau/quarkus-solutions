package org.formation;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class LivraisonResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/livraisons")
          .then()
             .statusCode(200);
            // .body(is("Hello from RESTEasy Reactive"));
    }

}