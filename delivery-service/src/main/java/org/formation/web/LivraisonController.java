package org.formation.web;

import java.util.List;


import lombok.RequiredArgsConstructor;
import org.formation.domain.Livraison;
import org.formation.interceptor.Logged;
import org.formation.service.LivraisonService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/livraisons")
@RequiredArgsConstructor
public class LivraisonController {

	private final LivraisonService livraisonService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
	@Logged
	public List<Livraison> findAll() {
		return livraisonService.findAll();
	}

	
}
