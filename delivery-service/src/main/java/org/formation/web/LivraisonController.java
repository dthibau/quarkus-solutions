package org.formation.web;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.formation.domain.Livraison;
import org.formation.interceptor.Logged;
import org.formation.service.LivraisonService;

import jakarta.ws.rs.core.MediaType;
import org.formation.web.views.LivraisonViews;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/livraisons")
@RequiredArgsConstructor
public class LivraisonController {

	private final LivraisonService livraisonService;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	@Logged
	@JsonView(LivraisonViews.Base.class)
	public List<Livraison> findAll() {
		return livraisonService.findAll();
	}

	@GET
	@Path("{id}")
	@JsonView(LivraisonViews.Complet.class)
	public Livraison findOne(@RestPath Long id) {
		return livraisonService.load(id).orElseThrow(() -> new EntityNotFoundException("No such Livraison " + id));
	}

	@POST
	@ResponseStatus(201)
	public Livraison create(@RestQuery String noCommande) {
		return livraisonService.create(noCommande);
	}

	@PATCH
	@Path("{id}/start")
	@ResponseStatus(204)
	public void start(@RestPath Long id) {
		livraisonService.start(id);
	}
	@PATCH
	@Path("{id}/affect/{livreurId}")
	@ResponseStatus(204)
	public void affect(@RestPath Long id, @RestPath Long livreurId) {
		livraisonService.affect(id, livreurId);
	}
}
