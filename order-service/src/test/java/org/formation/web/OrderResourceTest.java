package org.formation.web;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.matchesPattern;

@QuarkusTest
public class OrderResourceTest {

    @Test
    void createOrder_valid_returns201() {
        given().contentType(ContentType.JSON).body(validPayload())
                .when().post("/orders")
                .then().statusCode(201)
                .header("Location", matchesPattern(".*/orders/\\d+"));
    }

    @Test
    void createOrder_invalid_returns400() {
        given().contentType(ContentType.JSON).body(badPayload())
                .when().post("/orders")
                .then().statusCode(400);
    }


    private String validPayload() {
        return " {\n" +
                " \"lineItems\": [\n" +
                "    {\n" +
                "      \"refProduct\": \"string\",\n" +
                "      \"price\": 0.1,\n" +
                "      \"quantity\": 0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"deliveryAddress\": {\n" +
                "    \"rue\": \"string\",\n" +
                "    \"ville\": \"string\",\n" +
                "    \"codePostal\": \"string\"\n" +
                "  },\n" +
                "  \"paymentInformation\": {\n" +
                "    \"paymentToken\": \"78979898900496\"\n" +
                "  }\n" +
                "}";
    }

    private String badPayload() {
        return " {\n" +
                " \"lineItems\": [\n" +
                "    {\n" +
                "      \"refProduct\": \"string\",\n" +
                "      \"price\": 0.1,\n" +
                "      \"quantity\": 0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"deliveryAddress\": {\n" +
                "    \"rue\": \"string\",\n" +
                "    \"ville\": \"string\",\n" +
                "    \"codePostal\": \"string\"\n" +
                "  },\n" +
                "  \"paymentInformation\": {\n" +
                "    \"paymentToken\": \"789796\"\n" +
                "  }\n" +
                "}";
    }

}
