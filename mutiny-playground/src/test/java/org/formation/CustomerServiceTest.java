package org.formation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    private CustomerService service;

    @BeforeEach
    void setUp() {
        service = new CustomerService(new LegacyCustomerService());
    }



    @Test
    void loadProfile_combinesCustomerOrderAndLoyalty() throws Exception {
        CustomerProfile profile = service.loadProfile(5L).await().atMost(Duration.ofSeconds(2));

        assertNotNull(profile);
        assertNotNull(profile.customer());
        assertEquals(5L, profile.customer().id());
        assertEquals("Customer-5", profile.customer().name());
        assertNotNull(profile.order());
        assertEquals(1005L, profile.order().id());
        assertNotNull(profile.loyalty());
        assertEquals("BRONZE", profile.loyalty().tier());
    }

}
