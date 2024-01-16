package org.formation;

import java.io.File;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Startup;

import jakarta.enterprise.context.ApplicationScoped;

@Startup
@ApplicationScoped
public class DiskSpaceHealthCheck implements HealthCheck{

    private static final long MINIMUM_DISK_SPACE_REQUIRED = 1_000_000_000; // 1 GB

    @Override
    public HealthCheckResponse call() {
        long freeDiskSpace = getFreeDiskSpace();

        if (freeDiskSpace >= MINIMUM_DISK_SPACE_REQUIRED) {
            return HealthCheckResponse
                    .named("disk-space")
                    .up()
                    .withData("freeDiskSpace", freeDiskSpace)
                    .build();
        } else {
            return HealthCheckResponse
                    .named("disk-space")
                    .down()
                    .withData("freeDiskSpace", freeDiskSpace)
                    .withData("message", "Insufficient disk space.")
                    .build();
        }
    }
    
    private long getFreeDiskSpace() {
        File root = new File("/");
        return root.getUsableSpace();
    }
}
