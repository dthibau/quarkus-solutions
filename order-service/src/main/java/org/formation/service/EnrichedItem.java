package org.formation.service;

import org.formation.domain.OrderItem;

public record EnrichedItem(OrderItem item, Product product, int stock) {}
