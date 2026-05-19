package org.formation;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

public class LegacyCustomerService {

    public CompletableFuture<Customer> findCustomerById(long id) {
        return CompletableFuture.supplyAsync(() -> {
            simulateLatency();
            if (id <= 0) {
                throw new IllegalArgumentException("Invalid customer id: " + id);
            }
            return new Customer(id, "Customer-" + id);
        });
    }

    public CompletableFuture<Order> findLastOrder(long customerId) {
        return CompletableFuture.supplyAsync(() -> {
            simulateLatency();
            return new Order(1000L + customerId, BigDecimal.valueOf(42.50).add(BigDecimal.valueOf(customerId)));
        });
    }

    public CompletableFuture<Loyalty> findLoyalty(long customerId) {
        return CompletableFuture.supplyAsync(() -> {
            simulateLatency();
            String tier = switch ((int) (customerId % 3)) {
                case 0 -> "GOLD";
                case 1 -> "SILVER";
                default -> "BRONZE";
            };
            return new Loyalty(tier);
        });
    }

    public CompletableFuture<CustomerProfile> loadProfile(long customerId) {
        return findCustomerById(customerId)
                .thenCompose(customer ->
                        findLastOrder(customer.id())
                                .thenCombine(findLoyalty(customer.id()),
                                        (order, loyalty) -> new CustomerProfile(customer, order, loyalty)))
                .exceptionally(ex -> CustomerProfile.empty(customerId));
    }

    private static void simulateLatency() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
