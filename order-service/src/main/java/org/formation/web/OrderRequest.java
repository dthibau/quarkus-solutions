package org.formation.web;

import lombok.Data;
import org.formation.domain.Address;
import org.formation.domain.OrderItem;
import org.formation.domain.PaymentInformation;

import java.util.List;

@Data
public class OrderRequest {

    List<OrderItem> lineItems;
    Address deliveryAddress;
    PaymentInformation paymentInformation;
}