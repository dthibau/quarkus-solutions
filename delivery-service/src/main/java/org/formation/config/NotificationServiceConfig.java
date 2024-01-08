package org.formation.config;

import java.util.Optional;

import org.hibernate.validator.constraints.Length;

import io.smallrye.config.ConfigMapping;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@ConfigMapping(prefix = "notification")
public interface NotificationServiceConfig {

    @Pattern(regexp = "http|https")
    default String protocol() {
        return "http";
    }

    @NotEmpty
    String host();

    Optional<String> port();

    Optional<String> rootUrl();
        
    @Length(min = 10)
    String token();

    default String completeUrl() {
        return protocol() + "://" + host() + ":" + port().orElse("80") + rootUrl().orElse("");
    }
}
