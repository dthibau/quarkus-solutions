package org.formation.web;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.formation.domain.Livraison;
import org.formation.service.LivraisonService;


public class LivraisonController {

	private LivraisonService livraisonService;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Livraison> findAll() {
		return livraisonService.findAll();
	}

	
}
