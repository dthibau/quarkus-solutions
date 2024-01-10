package org.formation;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class LivraisonResourceTest {
    @Test
    void wheGetLivraisonsCheckStatusAndSizeofArray() {
        given()
          .when().get("/livraisons")
          .then()
             .statusCode(200)
             .body("$", hasSize(4))
             .body("[0].id", equalTo(1));

    }

}