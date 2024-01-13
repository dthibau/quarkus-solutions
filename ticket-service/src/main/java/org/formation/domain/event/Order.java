package org.formation.domain.event;

import java.util.List;

import org.formation.domain.ProductRequest;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class Order {
    long id;

    @JsonAlias({ "orderItems", "productRequests" })
    List<ProductRequest> productRequests;
}
