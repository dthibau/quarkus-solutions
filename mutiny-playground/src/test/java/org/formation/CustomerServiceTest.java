package org.formation;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CustomerServiceTest {

    @Inject
    private CustomerService service;

    @Test
    void loadProfile_returnsAggregatedData() {
        UniAssertSubscriber<CustomerProfile> subscriber =
                service.loadProfile(42L)
                        .subscribe().withSubscriber(UniAssertSubscriber.create());

        CustomerProfile profile = subscriber
                .awaitItem()
                .assertCompleted()
                .getItem();

        assertThat(profile.customer().id()).isEqualTo(42L);
        assertThat(profile.customer().name()).isEqualTo("Customer-42");
        assertThat(profile.order()).isNotNull();
        assertThat(profile.customer().id()).isEqualTo(42L);
        assertThat(profile.loyalty()).isNotNull();
        assertThat(profile.loyalty().tier()).isIn("BRONZE", "SILVER", "GOLD");
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
