package org.formation.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.formation.domain.Address;
import org.formation.domain.OrderItem;
import org.formation.domain.PaymentInformation;

import java.util.List;

@Data
public class OrderRequest {

    @NotEmpty
    @Valid
    List<OrderItem> lineItems;

    @NotNull
    @Valid
    Address deliveryAddress;

    @Valid
    PaymentInformation paymentInformation;
}
