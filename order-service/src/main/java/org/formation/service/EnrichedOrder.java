package org.formation.service;

import org.formation.domain.Order;

import java.util.List;

public record EnrichedOrder(Order order, List<EnrichedItem> items) {}
