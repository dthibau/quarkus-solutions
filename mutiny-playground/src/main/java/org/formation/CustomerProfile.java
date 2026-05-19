package org.formation;

public record CustomerProfile(Customer customer, Order order, Loyalty loyalty) {

    public static CustomerProfile empty(long id) {
        return new CustomerProfile(new Customer(id, "unknown"), null, null);
    }
}
