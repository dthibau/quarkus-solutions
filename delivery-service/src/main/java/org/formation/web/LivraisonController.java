package org.formation.web;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.formation.domain.Livraison;
import org.formation.interceptor.Logged;
import org.formation.service.LivraisonService;


@Path("/livraisons")
public class LivraisonController {

	@Inject
	private LivraisonService livraisonService;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Logged
	public List<Livraison> findAll() {
		return livraisonService.findAll();
	}

	
}
