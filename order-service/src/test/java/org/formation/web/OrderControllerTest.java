package org.formation.web;

import org.formation.domain.Address;
import org.formation.domain.DeliveryInformation;
import org.formation.domain.OrderItem;
import org.formation.domain.PaymentInformation;
import org.junit.jupiter.api.Test;

import jakarta.ws.rs.core.MediaType;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;

import java.time.LocalDateTime;
import java.util.Collections;

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
        PaymentInformation paymentInformation = new PaymentInformation();
        paymentInformation.setPaymentToken("1234567890123456");
        DeliveryInformation deliveryInformation = new DeliveryInformation();
        deliveryInformation.setAddress(new Address());
        deliveryInformation.setDeliveryTime(LocalDateTime.now());
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(1);
        orderItem.setRefProduct("REF");
        orderItem.setPrice(10.0f);
        OrderRequest request = new OrderRequest();
        request.setPaymentInformation(paymentInformation);
        request.setDeliveryAddress(deliveryInformation.getAddress());
        request.setLineItems(Collections.singletonList(orderItem));
        given()
        .when()
        .contentType(MediaType.APPLICATION_JSON)
                .body(request)
        .post("")
        .then()
           .statusCode(201)
           .body("id", notNullValue());
    }

}
