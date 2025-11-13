package org.formation.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void should_return_only_discounted_orders(UniAsserter asserter) {
        Order noDiscount = Order.builder().discount(0f).build();
        Order nullDiscount = Order.builder().build();
        Order tenPercent = Order.builder().discount(10f).build();
        Order fivePercent = Order.builder().discount(5f).build();

        // Reset propre par test (rollback assuré par @TestReactiveTransaction)
        asserter.execute(orderRepository::deleteAll);

        // Données de test
        asserter.execute(() -> orderRepository.persist(noDiscount));
        asserter.execute(() -> orderRepository.persist(nullDiscount));
        asserter.execute(() -> orderRepository.persist(tenPercent));
        asserter.execute(() -> orderRepository.persistAndFlush(fivePercent));

        // Assertions sur le résultat
        asserter.assertThat(
                () -> orderRepository.findOrdersWithDiscount(),
                (List<Order> result) -> {
                    assertEquals(2, result.size(), "On attend uniquement les commandes avec discount > 0");
                    assertTrue(result.stream().allMatch(o -> o.getDiscount() > 0));
                }
        );
    }


    @Test
    @TestReactiveTransaction
    void should_filter_orders_posterior_to_cutoff(UniAsserter asserter) {
        Instant now = Instant.now();
        Instant yesterday = now.minus(Duration.ofDays(1));
        Instant tomorrow = now.plus(Duration.ofDays(1)); // (le code original n’affectait pas la valeur)

        Order past = Order.builder().date(yesterday).build();
        Order future = Order.builder().date(tomorrow).build();

        asserter.execute(orderRepository::deleteAll);
        asserter.execute(() -> orderRepository.persist(past));
        asserter.execute(() -> orderRepository.persistAndFlush(future));

        asserter.assertThat(
                () -> orderRepository.findOrdersPosteriorTo(now),
                (List<Order> result) -> {
                    assertEquals(1, result.size(), "Seule la commande future doit être renvoyée");
                    assertEquals(tomorrow, result.get(0).getDate());
                }
        );
    }

}
