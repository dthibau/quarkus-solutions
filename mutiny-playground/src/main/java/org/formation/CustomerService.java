package org.formation;

import io.smallrye.mutiny.Multi;
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
                                                .onFailure().retry()
                                                    .withBackOff(Duration.ofMillis(100), Duration.ofSeconds(2))
                                                    .withJitter(0.5).atMost(3)
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

    public Uni<CustomerProfile> loadProfileTraced(long customerId) {
        return Uni.createFrom().completionStage(() -> legacyCustomerService.findCustomerById(customerId))
                .onItem().invoke(c -> System.out.println("  [trace] customer chargé : " + c.id()))
                .onItem().transformToUni(customer ->
                        Uni.createFrom().completionStage(() -> legacyCustomerService.findLastOrder(customer.id()))
                                .onItem().invoke(o -> System.out.println("  [trace] commande chargée"))
                                .onItem().transformToUni(order ->
                                        Uni.createFrom().completionStage(() -> legacyCustomerService.findLoyalty(customer.id()))
                                                .onItem().invoke(l -> System.out.println("  [trace] fidélité : " + l.tier()))
                                                .onItem().transform(loyalty ->
                                                        new CustomerProfile(customer, order, loyalty))));
    }

    public Multi<Customer> loadCustomers(int count) {
        // On part d'un Multi qui émet les IDs 1, 2, ..., count
        // Pour chaque ID, on déclenche un appel asynchrone qui renvoie un Uni<Customer>
        // transformToUniAndConcatenate convertit chaque Uni en élément du flux résultant
        return Multi.createFrom().range(1, count + 1)
                .onItem().transformToUniAndConcatenate(id ->
                        Uni.createFrom().completionStage(() -> legacyCustomerService.findCustomerById(id)));
    }

    public Uni<Long> demoNoMemoize(long customerId) {
        // Ce Uni rejoue findCustomerById à chaque souscription
        Uni<Customer> customerUni = Uni.createFrom()
                .completionStage(() -> legacyCustomerService.findCustomerById(customerId));

        // On s'abonne 3 fois au même Uni
        Uni<Customer> sub1 = customerUni.onItem().invoke(c -> System.out.println("  [no-memoize] sub1"));
        Uni<Customer> sub2 = customerUni.onItem().invoke(c -> System.out.println("  [no-memoize] sub2"));
        Uni<Customer> sub3 = customerUni.onItem().invoke(c -> System.out.println("  [no-memoize] sub3"));

        return Uni.combine().all().unis(sub1, sub2, sub3)
                .with((c1, c2, c3) -> c1.id());
    }

    public Uni<Long> demoMemoize(long customerId) {
        // Ce Uni rejoue findCustomerById à chaque souscription
        Uni<Customer> customerUni = Uni.createFrom()
                .completionStage(() -> legacyCustomerService.findCustomerById(customerId))
                .memoize().indefinitely();

        // On s'abonne 3 fois au même Uni
        Uni<Customer> sub1 = customerUni.onItem().invoke(c -> System.out.println("  [memoize] sub1"));
        Uni<Customer> sub2 = customerUni.onItem().invoke(c -> System.out.println("  [memoize] sub2"));
        Uni<Customer> sub3 = customerUni.onItem().invoke(c -> System.out.println("  [memoize] sub3"));

        return Uni.combine().all().unis(sub1, sub2, sub3)
                .with((c1, c2, c3) -> c1.id());
    }
}
