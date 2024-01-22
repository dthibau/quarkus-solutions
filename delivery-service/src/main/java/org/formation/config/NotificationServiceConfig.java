package org.formation.config;

import java.util.Optional;

import org.hibernate.validator.constraints.Length;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@ConfigMapping(prefix = "notification")
public interface NotificationServiceConfig {

    @Pattern(regexp = "http|https")
    @WithDefault("http")
    String protocol();

    @NotEmpty
    String host();

    @WithDefault("8080")
    int port();

    Optional<String> rootUrl();
        
    @Length(min = 10)
    String token();

    default String completeUrl() {
        return protocol() + "://" + host() + ":" + port() + rootUrl().orElse("");
    }
}
