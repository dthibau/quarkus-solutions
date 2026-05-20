package org.formation.service;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "catalog-api")
@Path("/products")
public interface CatalogClient {
    @GET
    @Path("/{ref}")
    Uni<Product> findById(@PathParam("ref") String ref);
}
