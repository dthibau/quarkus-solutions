package org.formation.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.formation.domain.TicketStatus;

import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class TicketService {

	@Inject
    ReactiveMongoClient mongoClient;

    public Uni<List<Ticket>> list() {
        return getCollection().find()
                .map(doc -> {
                    Ticket ticket = new Ticket();
                    ticket.setStatus(TicketStatus.valueOf(doc.getString("status")));
                    ticket.setOrderId(doc.getString("orderId"));
                    return ticket;
                }).collect().asList();
    }
    
    public Uni<UpdateResult> readyToPickUpReactive(String ticketId) {
		Document query = new Document("_id", new ObjectId(ticketId));
		return getCollection().updateOne(query, Updates.set("status",TicketStatus.READY_TO_PICK.name()));
	}
	
    public Uni<InsertOneResult> createTicketReactive(Long orderId, List<ProductRequest> productsRequest) {
		Document document = new Document();
		document.put("orderId", orderId);
		
		
		return getCollection().insertOne(document);
	}
    
    private ReactiveMongoCollection<Document> getCollection() {
        return mongoClient.getDatabase("tickets").getCollection("Ticket");
    }
    
	public Ticket createTicket(Long orderId, List<ProductRequest> productsRequest) {
		Ticket t = new Ticket();
		t.setOrderId(""+orderId);
		t.setProductRequests(productsRequest);
		t.setStatus(TicketStatus.CREATED);
		
		Ticket.persist(t);
		
		return t;
	}
	
	
	public Ticket readyToPickUp(String ticketId) {

		Ticket t = Ticket.findById(new ObjectId(ticketId));

		t.setStatus(TicketStatus.READY_TO_PICK);
		
		Ticket.persistOrUpdate(t);
		
		return t;
		

	}
}
