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

@QuarkusTest
@TestReactiveTransaction
public class OrderServiceTest {

    @Inject
    OrderService orderService;

    @Test
    void testCreateOrder(UniAsserter asserter) {
        var items = List.of(
                OrderItem.builder().refProduct("SKU-1").quantity(2).price(1500).build(),
                OrderItem.builder().refProduct("SKU-2").quantity(1).price(2999).build()
        );

        var address = Address.builder()
                .rue("10 rue de la Paix")
                .codePostal("75002")
                .ville("Paris")
                .build();

        var payment = PaymentInformation.builder()
                .paymentToken("CARD_txn_123")
                .build();

        asserter.assertThat(
                () -> orderService.createOrder(items, address, payment),
                order -> {
                    assert order != null;
                    assert order.getId() > 0;
                    assert order.getOrderItems().size() == 2;
                    assert order.getDeliveryInformation() != null;
                    assert order.getDeliveryInformation().getAddress().equals(address);
                    assert order.getPaymentInformation().equals(payment);
                }
        );

    }
}
