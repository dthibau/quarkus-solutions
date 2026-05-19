package org.formation;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;

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
    @GET
    @Path("{id}/traced")
    public Uni<CustomerProfile> getTraced(@PathParam("id") long id) {
        return customerService.loadProfileTraced(id);
    }

    @GET
    @Path("/batch/{count}")
    public Uni<List<Customer>> loadBatch(@PathParam("count") int count) {
        return customerService.loadCustomers(count)
                .collect().asList();
    }

    @GET
    @Path("{id}/no-memoize")
    public Uni<Long> getNoMemoize(@PathParam("id") long id) {
        return customerService.demoNoMemoize(id);
    }

    @GET
    @Path("{id}/memoize")
    public Uni<Long> getMemoize(@PathParam("id") long id) {
        return customerService.demoMemoize(id);
    }
}
