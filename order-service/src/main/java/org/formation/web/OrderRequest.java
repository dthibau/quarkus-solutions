package org.formation.web;

import java.util.List;

import org.formation.domain.Address;
import org.formation.domain.OrderItem;
import org.formation.domain.PaymentInformation;

import lombok.Data;

@Data
public class OrderRequest {
    
    List<OrderItem> lineItems;
    Address deliveryAddress;
    PaymentInformation paymentInformation;
}
