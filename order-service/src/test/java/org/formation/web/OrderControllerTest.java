package org.formation.web;

import org.junit.jupiter.api.Test;

import jakarta.ws.rs.core.MediaType;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestHTTPEndpoint(OrderController.class)
public class OrderControllerTest {
    
    
    @Test
    public void whenGetOrders_thenSuccess() {
        given()
        .when().get("")
        .then()
           .statusCode(200);
      

    }

    @Test
    public void whenPostOrder_thenSuccess() {
        given()
        .when()
        .contentType(MediaType.APPLICATION_JSON)
        .body("{\"lineItems\":[{\"quantity\":1,\"productId\":1}],\"deliveryAddress\":{\"street\":\"rue de la paix\",\"city\":\"Paris\",\"country\":\"France\"},\"paymentInformation\":{\"cardNumber\":\"1234567890123456\",\"expiryDate\":\"12/22\",\"cardHolder\":\"John Doe\"}}")
        .post("")
        .then()
           .statusCode(201)
           .body("id", notNullValue());
    }

}
