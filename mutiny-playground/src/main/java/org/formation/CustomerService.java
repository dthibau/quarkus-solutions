package org.formation;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class CustomerService {

    private final LegacyCustomerService legacyCustomerService;

    public CustomerService(LegacyCustomerService legacyCustomerService) {
        this.legacyCustomerService = legacyCustomerService;
    }

    public Uni<CustomerProfile> loadProfileResilient(long customerId) {
        return Uni.createFrom().completionStage(() -> legacyCustomerService.findCustomerById(customerId))
                .onItem().transformToUni(customer ->
                        Uni.createFrom().completionStage(() -> legacyCustomerService.findLastOrder(customer.id()))
                                .onItem().transformToUni(order ->
                                        // Appel au service instable, avec résilience
                                        Uni.createFrom().completionStage(() -> legacyCustomerService.findLoyaltyUnstable(customer.id()))
                                                .ifNoItem().after(Duration.ofMillis(500))
                                                .failWith(new RuntimeException("Loyalty trop lent"))
                                                .onFailure().retry().atMost(3)
                                                .onFailure().recoverWithItem(Loyalty.unknown())
                                                .onItem().transform(loyalty ->
                                                        new CustomerProfile(customer, order, loyalty))));
    }

    public Uni<CustomerProfile> loadProfileParallel(long customerId) {
        long start = System.currentTimeMillis();
        return Uni.createFrom()
                .completionStage(() -> legacyCustomerService.findCustomerById(customerId))
                .onItem().transformToUni(customer -> {
// Les 2 appels suivants ne dépendent pas l'un de l'autre.
// On les lance en parallèle avec Uni.combine().
                    Uni<Order> orderUni = Uni.createFrom()
                            .completionStage(() -> legacyCustomerService.findLastOrder(customer.id()));
                    Uni<Loyalty> loyaltyUni = Uni.createFrom()
                            .completionStage(() -> legacyCustomerService.findLoyalty(customer.id()));
                    return Uni.combine().all().unis(orderUni, loyaltyUni)
                            .asTuple()
                            .onItem().transform(tuple -> new CustomerProfile(customer, tuple.getItem1(), tuple.getItem2()));
                })
                .onFailure().recoverWithItem(CustomerProfile.empty(customerId))
                .onItem().invoke(profile ->
                        System.out.println("[parallel] " + (System.currentTimeMillis() - start) + " ms"));
    }

    public Uni<CustomerProfile> loadProfileSequential(long customerId) {
        long start = System.currentTimeMillis();
        return loadProfile(customerId)
                .onItem().invoke(profile ->
                        System.out.println("[sequential] " + (System.currentTimeMillis() - start) + " ms"));
    }

    public Uni<CustomerProfile> loadProfile(long customerId) {
        Uni<Customer> customerUni = Uni.createFrom()
                .completionStage(() -> legacyCustomerService.findCustomerById(customerId));

        return customerUni
                .onItem().transformToUni(customer ->
                        Uni.createFrom().completionStage(() -> legacyCustomerService.findLastOrder(customer.id()))
                                .onItem().transformToUni(order ->
                                        Uni.createFrom().completionStage(() -> legacyCustomerService.findLoyalty(customer.id()))
                                                .onItem().transform(loyalty ->
                                                        new CustomerProfile(customer, order, loyalty))))
                .onFailure().recoverWithItem(CustomerProfile.empty(customerId));
    }
}
