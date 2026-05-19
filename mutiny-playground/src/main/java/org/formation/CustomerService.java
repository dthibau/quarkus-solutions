package org.formation;

import io.smallrye.mutiny.Uni;

import java.util.concurrent.CompletableFuture;

public class CustomerService {

    private final LegacyCustomerService legacyCustomerService;

    public CustomerService(LegacyCustomerService legacyCustomerService) {
        this.legacyCustomerService = legacyCustomerService;
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
