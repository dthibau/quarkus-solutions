package org.formation;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import java.net.URL;
import org.formation.web.LivraisonController;

@QuarkusTest
@TestHTTPEndpoint(LivraisonController.class)
class LivraisonResourceTest {

	@TestHTTPResource("")
	URL livraisonsUrl;




    @Test
    void whenGetLivraisonsCheckStatusAndSizeofArray() {
        given()
          .when().get()
          .then()
             .statusCode(200);
//             .body("$", hasSize(1))
//             .body("[0].id", equalTo(1));

    }
    @Test
    void whenCreateLivraisonsCheckStatusAndId() {
        
        given()
                .contentType(JSON)
                .queryParam("noCommande", "1234")
          .when().post()
          .then()
             .statusCode(201)
             .body("id", notNullValue())
             .body("noCommande", equalTo("1234"))
             .body("status", equalTo("CREE"));
        

    }
}