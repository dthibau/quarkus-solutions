package org.formation.service;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;
import org.formation.domain.*;
import org.junit.jupiter.api.Test;


import java.time.Instant;
import java.util.List;

import static org.hibernate.reactive.common.Identifier.id;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class OrderServiceTest {

    @Inject
    OrderService orderService;

    @Inject
    OrderRepository orderRepository;

    @Inject
    ReactiveRedisDataSource redis;


    @Test
    @TestReactiveTransaction
    void shouldCreateOrder(UniAsserter asserter) {
        // GIVEN
        Address address = new Address();
        address.setRue("10 rue de Belleville");
        address.setVille("Paris");
        address.setCodePostal("75019");

        PaymentInformation payment = new PaymentInformation();
        payment.setPaymentToken("1234567890ABCDEFG");

        OrderItem item = new OrderItem();
        item.setRefProduct("PROD");
        item.setQuantity(1);

        List<OrderItem> items = List.of(item);

        // WHEN / THEN
        asserter.assertThat(
                () -> orderService.createOrder(items, address, payment),
                order -> {

                    assertNotNull(order);
                    assertTrue(order.getId() > 0);
                    assertNotNull(order.getId());
                    assertEquals(1,order.getOrderItems().size());
                    assertEquals(
                            "Paris",
                            order.getDeliveryInformation()
                                    .getAddress()
                                    .getVille()
                    );
                    assertEquals(
                            "1234567890ABCDEFG",
                            order.getPaymentInformation()
                                    .getPaymentToken()
                    );
                }
        );
    }

    @Test
    @TestReactiveTransaction
    void leSecondAppelEstServiParLeCache(UniAsserter asserter) {
        var cache = redis.value(Order.class);

        asserter.execute(() -> Panache.withTransaction(() -> {
            Order order = Order.builder().date(Instant.now()).build();
            return orderRepository.persist(order)
                    .invoke(() -> asserter.putData("id", order.getId()));
        }));

        asserter.assertThat(
                () -> Panache.withTransaction(() -> orderService.findById((Long)asserter.getData("id"))),
                order -> assertEquals((Long)asserter.getData("id"), order.order().getId()));

        asserter.assertNotNull(() -> cache.get("order:" + (Long)asserter.getData("id")));   // pas de tx ici

        asserter.execute(() -> Panache.withTransaction(() -> orderRepository.deleteById((Long)asserter.getData("id"))));

        asserter.assertThat(
                () -> Panache.withTransaction(() -> orderService.findById((Long)asserter.getData("id"))),
                order -> assertEquals((Long)asserter.getData("id"), order.order().getId()));

        asserter.execute(() -> redis.key().del("order:" + (Long)asserter.getData("id")));
    }

}