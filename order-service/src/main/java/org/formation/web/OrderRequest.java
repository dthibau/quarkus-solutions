package org.formation.web;

import java.util.List;

import org.formation.domain.Address;
import org.formation.domain.OrderItem;
import org.formation.domain.PaymentInformation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {
    
    @NotEmpty
    List<@Valid OrderItem> lineItems;
    
    @NotNull
    @Valid
    Address deliveryAddress;

    @NotNull
    @Valid
    PaymentInformation paymentInformation;
}
