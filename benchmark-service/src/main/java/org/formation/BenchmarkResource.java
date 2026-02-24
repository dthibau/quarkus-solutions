package org.formation;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.Duration;

@Path("/bench")
@Produces(MediaType.APPLICATION_JSON)
public class BenchmarkResource {
    // ---------- 1) Impératif (bloquant, worker thread) ----------
    @GET
    @Path("/imperative")
    @Blocking // force le worker-pool
    public ResponseDto imperative() {
        sleep(200); // Simule une E/S bloquante 200 ms
        return new ResponseDto("imperative", 200);
    }
    // ---------- 2) Réactif (non-bloquant, event loop) ----------
    @GET
    @Path("/reactive")
    public Uni<ResponseDto> reactive() {
// Timer non-bloquant: l'event-loop n'est pas occupé pendant l'attente
        return Uni.createFrom().voidItem()
                .onItem().delayIt().by(Duration.ofMillis(200))
                .onItem().transform(x -> new ResponseDto("reactive", 200));
    }
    // ---------- 3) Virtual Threads (Loom) ----------
    @GET
    @Path("/virtual")
    @RunOnVirtualThread // Chaque requête s'exécute sur un virtual thread
    public ResponseDto virtualThreads() {
        sleep(200); // Même coût “E/S” mais sans monopoliser un worker physique
        return new ResponseDto("virtual", 200);
    }
    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
    public record ResponseDto(String model, long simulatedLatencyMs) {}
}
