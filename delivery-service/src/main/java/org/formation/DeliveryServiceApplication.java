package org.formation;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.quarkus.arc.profile.UnlessBuildProfile;

@ApplicationPath("/api/v1")
@UnlessBuildProfile("test")
public class DeliveryServiceApplication extends Application {

}
