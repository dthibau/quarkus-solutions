package org.formation;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/customers")
public class CustomerResource {

    private final CustomerService customerService;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GET
    @Path("{id}/sequential")
    public Uni<CustomerProfile> getSequential(@PathParam("id") long id) {
        return customerService.loadProfileSequential(id);
    }

    @GET
    @Path("{id}/parrallel")
    public Uni<CustomerProfile> getParrallel(@PathParam("id") long id) {
        return customerService.loadProfileParallel(id);
    }

    @GET
    @Path("{id}/resilient")
    public Uni<CustomerProfile> getResilient(@PathParam("id") long id) {
        return customerService.loadProfileResilient(id);
    }
}
