package org.formation;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.formation.web.LivraisonController;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static io.restassured.RestAssured.given;

@QuarkusIntegrationTest
class LivraisonResourceIT  {

    @TestHTTPEndpoint(LivraisonController.class)
    @TestHTTPResource
    URL endpoint;

    @Test
    void testLivraisonsEndpoint() {
        given()
                .when().get(endpoint)
                .then()
                .statusCode(200);
        // .body(is("Hello from RESTEasy Reactive"));
    }
}
