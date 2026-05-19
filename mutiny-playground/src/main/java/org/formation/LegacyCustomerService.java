package org.formation;

import jakarta.enterprise.context.Dependent;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Dependent
public class LegacyCustomerService {

    private final AtomicInteger callCount = new AtomicInteger(0);

    /**
     * Variante "instable" de findLoyalty : simule un service réel défaillant.
     *  - 60 % des appels échouent avec une RuntimeException
     *  - 20 % des appels prennent 1 seconde au lieu de 100 ms (lenteur anormale)
     *  - 20 % des appels répondent normalement
     *
     * Utilisée pour démontrer retry, timeout et fallback.
     */
    public CompletableFuture<Loyalty> findLoyaltyUnstable(long customerId) {
        return CompletableFuture.supplyAsync(() -> {
            int call = callCount.incrementAndGet();
            double dice = ThreadLocalRandom.current().nextDouble();

            if (dice < 0.6) {
                simulateLatency(50);
                System.out.println("  [findLoyaltyUnstable #" + call + "] échec");
                throw new RuntimeException("Service de fidélité indisponible");
            }
            if (dice < 0.8) {
                System.out.println("  [findLoyaltyUnstable #" + call + "] lenteur anormale");
                simulateLatency(1000);
            } else {
                System.out.println("  [findLoyaltyUnstable #" + call + "] succès");
                simulateLatency(100);
            }
            String tier = (customerId % 3 == 0) ? "GOLD"
                    : (customerId % 2 == 0) ? "SILVER" : "BRONZE";
            return new Loyalty(tier);
        });
    }

    public CompletableFuture<Customer> findCustomerById(long id) {
        System.out.println("findByCustomerId");
        return CompletableFuture.supplyAsync(() -> {
            simulateLatency(100);
            if (id <= 0) {
                throw new IllegalArgumentException("Invalid customer id: " + id);
            }
            return new Customer(id, "Customer-" + id);
        });
    }

    public CompletableFuture<Order> findLastOrder(long customerId) {
        return CompletableFuture.supplyAsync(() -> {
            simulateLatency(100);
            return new Order(1000L + customerId, BigDecimal.valueOf(42.50).add(BigDecimal.valueOf(customerId)));
        });
    }

    public CompletableFuture<Loyalty> findLoyalty(long customerId) {
        return CompletableFuture.supplyAsync(() -> {
            simulateLatency(100);
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

    private static void simulateLatency(long latency) {
        try {
            Thread.sleep(latency);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
