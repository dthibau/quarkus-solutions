package org.formation.service;


import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.logging.Log;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.formation.domain.*;

import java.time.Instant;
import java.util.List;

@ApplicationScoped
@WithTransaction
public class OrderService {

    private final OrderRepository orderRepository;
    private final EventRepository eventRepository;
    private final ReactiveValueCommands<String, Order> cache;

    @RestClient
    CatalogClient catalogClient;
    @RestClient
    StockClient   stockClient;

    public OrderService(OrderRepository orderRepository, EventRepository eventRepository,
                        ReactiveRedisDataSource redis) {
        this.orderRepository = orderRepository;
        this.eventRepository = eventRepository;
        this.cache = redis.value(Order.class);
    }

    public Uni<EnrichedOrder> findById(long id) {
        String key = "order:" + id;
        return cache.get(key)
                .onItem().ifNull().switchTo(() ->
                        orderRepository.findById(id)
                                .onItem().ifNull().failWith(() -> new EntityNotFoundException(id + " introuvable"))
                                .call(o -> cache.setex(key, 60, o)))
                .flatMap(this::enrich);   // <-- enrichissement, après cache ET DB
    }

    private Uni<EnrichedOrder> enrich(Order order) {
        List<Uni<EnrichedItem>> enrichedUnis = order.getOrderItems().stream()
                .map(this::enrichItem)
                .toList();

        return Uni.join().all(enrichedUnis).andFailFast()
                .map(items -> new EnrichedOrder(order, items));
    }

    private Uni<EnrichedItem> enrichItem(OrderItem item) {
        Uni<Product> productUni = catalogClient.findById(item.getRefProduct())
                .onSubscription().invoke(() -> Log.infof("→ [%s] CATALOG", item.getRefProduct()));

        Uni<Integer> stockUni = stockClient.available(item.getRefProduct())
                .onSubscription().invoke(() -> Log.infof("→ [%s] STOCK", item.getRefProduct()));

        return Uni.combine().all()
                .unis(productUni, stockUni)
                .asTuple()
                .map(t -> new EnrichedItem(item, t.getItem1(), t.getItem2()));
    }

    public Uni<Order> createOrder(List<OrderItem> lineItems, Address deliveryAddress, PaymentInformation paymentInformation) {
        DeliveryInformation df = new DeliveryInformation();
        df.setAddress(deliveryAddress);

        Order order = Order.builder().date(Instant.now())
                .paymentInformation(paymentInformation)
                .deliveryInformation(df)
                .orderItems(lineItems).build();

        return orderRepository.persistAndFlush(order)
                .chain(persistedOrder -> {

                    Event event = new Event();
                    event.setEntityId(persistedOrder.getId().toString());
                    event.setEventName("ORDER_CREATED");
                    event.setDate(Instant.now());

                    return eventRepository.persist(event)
                            .replaceWith(persistedOrder);
                });
    }
}
