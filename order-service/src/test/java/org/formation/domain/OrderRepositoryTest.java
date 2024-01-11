package org.formation.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;

@QuarkusTest
// @TestReactiveTransaction
public class OrderRepositoryTest {

    @Inject
    OrderRepository orderRepository;

    @Test
    @TestReactiveTransaction
    public void testDiscount(UniAsserter asserter) {
        Order noDiscountOrder = Order.builder().build();
        Order discountOrder = Order.builder().discount(10.0f).build();
        asserter.execute(() -> orderRepository.persist(noDiscountOrder));
        asserter.execute(() -> orderRepository.persistAndFlush(discountOrder));

        asserter.assertNotNull(() -> orderRepository.findOrdersWithDiscount()); 
    }

    @Test
    @TestReactiveTransaction
    public void testPosterior(UniAsserter asserter) {
        Instant tomorrow = Instant.now();
        tomorrow.plus(Duration.ofDays(1));

        Order futurOrder = Order.builder().date(tomorrow).build();
        asserter.execute(() -> orderRepository.persist(futurOrder));

        asserter.assertNotNull(() -> orderRepository.findOrdersPosteriorTo(Instant.now())); 
    }
}
