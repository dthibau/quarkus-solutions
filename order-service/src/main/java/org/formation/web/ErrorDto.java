package org.formation.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.formation.domain.Address;
import org.formation.domain.OrderItem;
import org.formation.domain.PaymentInformation;

import java.util.List;

@Data
@Builder
public class ErrorDto {

    String type;

    String detailMessage;
}
