package com.storyscawler.infrastructure.property;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = JsoupExecutorPoolProperties.PREFIX)
public class JsoupExecutorPoolProperties {
    protected static final String PREFIX = "app.jsoup-executor.pool";

    @NotNull
    private PoolOptions storyDetail;
    @NotNull
    private PoolOptions storyListing;

    @Getter
    @Setter
    @Validated
    public static class PoolOptions {
        @NotNull
        private int maxSize;
    }
}
