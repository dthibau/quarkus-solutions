package org.formation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.formation.domain.TicketStatus;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class TicketServiceTest {
    
    @Inject
    TicketService ticketService;

    @Test
    public void testCreateTicket() {
        long initialCount = Ticket.count();

        Ticket t = ticketService.createTicket(1L, List.of(new ProductRequest(1L, "REF",1)));
        assertNotNull(t);
        assertEquals(1L, t.getOrderId());
        assertEquals(1, t.getProductRequests().size());
        assertEquals(TicketStatus.CREATED, t.getStatus());
        assertEquals(initialCount+1, Ticket.count());
    }

     @Test
    public void testReadyToPickup() {

        Ticket t = ticketService.createTicket(1L, List.of(new ProductRequest(1L, "REF",1)));
        assertNotNull(t);
        assertEquals(1L, t.getOrderId());
        assertEquals(1, t.getProductRequests().size());
        assertEquals(TicketStatus.CREATED, t.getStatus());

        Ticket t2 = ticketService.readyToPickUp(t.id.toString());
        assertNotNull(t2);
        assertEquals(1L, t2.getOrderId());
        assertEquals(1, t2.getProductRequests().size());
        assertEquals(TicketStatus.READY_TO_PICK, t2.getStatus());
    }
}
