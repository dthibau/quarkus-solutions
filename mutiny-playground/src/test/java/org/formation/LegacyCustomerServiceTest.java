package org.formation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class LegacyCustomerServiceTest {

    private LegacyCustomerService service;

    @BeforeEach
    void setUp() {
        service = new LegacyCustomerService();
    }

    @Test
    void findCustomerById_returnsCustomer() throws Exception {
        Customer customer = service.findCustomerById(42L).get(2, TimeUnit.SECONDS);

        assertNotNull(customer);
        assertEquals(42L, customer.id());
        assertEquals("Customer-42", customer.name());
    }

    @Test
    void findLastOrder_returnsOrder() throws Exception {
        Order order = service.findLastOrder(7L).get(2, TimeUnit.SECONDS);

        assertNotNull(order);
        assertEquals(1007L, order.id());
    }

    @Test
    void findLoyalty_returnsLoyalty() throws Exception {
        Loyalty loyalty = service.findLoyalty(3L).get(2, TimeUnit.SECONDS);

        assertNotNull(loyalty);
        assertEquals("GOLD", loyalty.tier());
    }

    @Test
    void loadProfile_combinesCustomerOrderAndLoyalty() throws Exception {
        CustomerProfile profile = service.loadProfile(5L).get(2, TimeUnit.SECONDS);

        assertNotNull(profile);
        assertNotNull(profile.customer());
        assertEquals(5L, profile.customer().id());
        assertEquals("Customer-5", profile.customer().name());
        assertNotNull(profile.order());
        assertEquals(1005L, profile.order().id());
        assertNotNull(profile.loyalty());
        assertEquals("BRONZE", profile.loyalty().tier());
    }

    @Test
    void loadProfile_fallsBackToEmptyOnFailure() throws ExecutionException, InterruptedException, TimeoutException {
        CustomerProfile profile = service.loadProfile(-1L).get(2, TimeUnit.SECONDS);

        assertNotNull(profile);
        assertNotNull(profile.customer());
        assertEquals(-1L, profile.customer().id());
        assertEquals("unknown", profile.customer().name());
        assertNull(profile.order());
        assertNull(profile.loyalty());
    }
}
