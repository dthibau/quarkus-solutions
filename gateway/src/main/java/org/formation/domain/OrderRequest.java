package org.formation.domain;

import java.util.List;

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
