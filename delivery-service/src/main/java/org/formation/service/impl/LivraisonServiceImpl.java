package org.formation.service.impl;

import java.net.URI;
import java.time.Instant;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.formation.config.NotificationServiceConfig;
import org.formation.domain.Livraison;
import org.formation.domain.Livreur;
import org.formation.domain.Status;
import org.formation.interceptor.Logged;
import org.formation.service.LivraisonService;
import org.formation.service.NotificationService;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
@Logged
public class LivraisonServiceImpl implements LivraisonService {

	@Inject
	NotificationServiceConfig notificationServiceConfig;
	
    @RestClient 
    NotificationService notificationService;
    
    private NotificationService notificationService2;
	
    @Inject
    EntityManager em;
    
	@ConfigProperty(name = "quarkus.http.port") 
	String port;
		
	@PostConstruct
	public void init() {
		notificationService2 = RestClientBuilder.newBuilder()
	            .baseUri(URI.create(notificationServiceConfig.url()))
	            .build(NotificationService.class);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Multi<Livraison> findAll() {
		return Livraison.findAll().stream();
	
	}
//	@SuppressWarnings("unchecked")
//	@Override
//	@Transactional
//	public List<Livraison> findAllSync() {
//		List<Livraison> ret = new ArrayList<>();
//		Livraison.findAll().stream().collect().in(ret, (col, item) -> col.add(item));
//		return ret;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public Uni<Livraison> load(Livraison livraison) {
		return Livraison.findById(livraison.getId());
//				.onItem();
//				.ifNull().failWith(() -> new NotFoundException());	
	}
		
	@Override
	@ReactiveTransactional
	public Uni<Livraison> create(String noCommande) {
		Livraison livraison = Livraison.builder().noCommande(noCommande).creationDate(Instant.now()).status(Status.CREE).build();
		return livraison.persist();
		
//		em.persist(livraison);
//		notificationService.sendMail(Courriel.builder().to("david.thibau@gmail.com").subject("Création Livraison").text(livraison.toString()).build());
//		notificationService2.sendMailReactive(Courriel.builder().to("david.thibau@gmail.com").subject("Création Livraison Builder").text(livraison.toString()).build())
//			.subscribe().with(e -> Log.info("Reactive Mail Sent " + e));
//
//		return livraison;
	}

	@Override
	@ReactiveTransactional
	public Uni<Livraison> affect(Livraison livraison, Livreur livreur) {
		return Livraison.<Livraison>findById(livraison.getId()).onItem().invoke((l) -> {
//			(Uni<Livraison>)Livraison.findById(livraison.getId())
//			.onItem();
			l.setLivreur(livreur);
		});
//		Livraison.update("livreur = ? where id=?" , livreur,livraison.getId()).subscribe();

	}

	@Override
	@ReactiveTransactional
	public Uni<Livraison> start(Livraison livraison) {
		return Livraison.<Livraison>findById(livraison.getId()).onItem().invoke((l) -> {
			l.setStatus(Status.EN_COURS);
		});
		
//		livraison = (Livraison)em.find(Livraison.class, livraison.getId());
//		livraison.setStatus(Status.EN_COURS);
	}

	@Override
	@ReactiveTransactional
	public Uni<Livraison> complete(Livraison livraison) {
		return Livraison.<Livraison>findById(livraison.getId()).onItem().invoke((l) -> {
			l.setStatus(Status.DISTRIBUE);
		});

	}

}
