package com.storyscawler.infrastructure.property;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = BunnyProperties.PREFIX)
public class BunnyProperties {
    protected static final String PREFIX = "app.bunny-net";

    @NotBlank
    private String baseUrl;
    @NotBlank
    private String apiKey;
    @NotBlank
    private String storageZoneName;
    @NotBlank
    private String storyThumbnailFolder;
}
