package org.formation.web;

import java.util.List;



import org.formation.domain.Livraison;
import org.formation.service.LivraisonService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/livraisons")
public class LivraisonController {

	private LivraisonService livraisonService;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Livraison> findAll() {
		return livraisonService.findAll();
	}

	
}
