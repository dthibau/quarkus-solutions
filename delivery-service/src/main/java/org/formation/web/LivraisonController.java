package org.formation.web;

import java.util.List;

import org.formation.domain.Livraison;
import org.formation.domain.Livreur;
import org.formation.interceptor.Logged;
import org.formation.service.LivraisonService;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import io.quarkus.logging.Log;


@Path("/livraisons")
public class LivraisonController {
	private final LivraisonService livraisonService;	

	public LivraisonController(LivraisonService livraisonService) {
		super();
		this.livraisonService = livraisonService;
	}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
	@Logged
	public List<Livraison> findAll() {
    	Log.debug("Message from Controller");
		return livraisonService.findAll();
	}

	@GET
    @Path("{id}")
    public Livraison findOne(@RestPath Long id) {
    	return livraisonService.load(id).orElseThrow(() -> new EntityNotFoundException("No such Livraison " + id));
    }


    @POST
    @ResponseStatus(201)
    public Livraison create(@RestQuery String noCommande) {
    	return livraisonService.create(noCommande);
    }
    

    @PUT
    @Path("{id}/start")
    @ResponseStatus(204)
    public void start(@RestPath Long id) {
    	livraisonService.start(id);
    }
    @PUT
    @Path("{id}/affect/{livreurId}")
    @ResponseStatus(204)
    public void affect(@RestPath Long id, @RestPath Long livreurId) {
    	livraisonService.affect(id, livreurId);
    }
	
}
