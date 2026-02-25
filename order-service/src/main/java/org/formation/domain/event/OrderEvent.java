package org.formation.domain.event;

import org.formation.domain.Order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderEvent {
   
    OrderEventType type;
    Order order;
}
