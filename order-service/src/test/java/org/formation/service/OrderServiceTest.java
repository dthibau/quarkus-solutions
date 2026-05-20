package org.formation.service;

import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;
import org.formation.domain.Address;
import org.formation.domain.OrderItem;
import org.formation.domain.PaymentInformation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class OrderServiceTest {

    @Inject
    OrderService orderService;

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
}