package org.formation.service;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "stock-api")
@Path("/products")
public interface StockClient {

    @GET
    @Path("/{ref}")
    Uni<Integer> available(@PathParam("ref") String ref);

}
