package org.formation.web;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.formation.domain.Livreur;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;
import java.util.Optional;

@Path("/livreurs")
@Transactional
public class LivreurController {

    // Créer un livreur
    @POST
    @ResponseStatus(201) // CREATED
    public Livreur createLivreur(@Valid Livreur livreur) {
        livreur.persist();
        return livreur;
    }

    // Lire un livreur par ID
    @GET
    @Path("/{id}")
    @ResponseStatus(200) // OK
    public Livreur getLivreur(@PathParam("id") Long id) {
        Optional<Livreur> optional = Livreur.findByIdOptional(id);
        return optional.orElseThrow(() -> new NotFoundException("Livreur not found"));
    }

    // Mettre à jour partiellement un livreur
    @PATCH
    @Path("/{id}")
    @ResponseStatus(200) // OK
    public Livreur updateLivreur(@RestPath("id") Long id, Livreur updated) {
        Optional<Livreur> optional = Livreur.findByIdOptional(id);
        Livreur existing = optional.orElseThrow(() -> new NotFoundException("Livreur not found"));
        if (updated.nom != null) {
            existing.nom = updated.nom;
        }
        if (updated.telephone != null) {
            existing.telephone = updated.telephone;
        }
        if (updated.status != null) {
            existing.status = updated.status;
        }
        if (updated.reviews != null) {
            existing.reviews = updated.reviews;
        }
        Livreur.getEntityManager().merge(existing);
        return existing;
    }

    // Remplacer un livreur
    @PUT
    @Path("/{id}")
    @ResponseStatus(200) // OK
    public Livreur replaceLivreur(@PathParam("id") Long id, Livreur replacement) {
        replacement.id = id;
        Livreur.getEntityManager().merge(replacement);
        return replacement;
    }

    // Supprimer un livreur
    @DELETE
    @Path("/{id}")
    @ResponseStatus(204) // NO CONTENT
    public void deleteLivreur(@PathParam("id") Long id) {
        Livreur.deleteById(id);
    }

    // Lister tous les livreurs
    @GET
    @ResponseStatus(200) // OK
    public List<Livreur> listLivreurs() {
        return Livreur.listAll();
    }
}