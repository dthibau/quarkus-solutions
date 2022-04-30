package org.formation.web;

import java.util.List;

import org.formation.domain.Livraison;
import org.formation.interceptor.Logged;
import org.formation.service.LivraisonService;

import jakarta.ws.rs.GET;
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

	
}
