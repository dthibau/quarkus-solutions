package org.formation;

import java.math.BigDecimal;

public record Order(long id, BigDecimal amount) {
}
