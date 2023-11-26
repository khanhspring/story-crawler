package com.storyscawler.infrastructure.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = AppConfigProperties.PREFIX)
public class AppConfigProperties {
    protected static final String PREFIX = "app.configs";

    private int storiesPageLimit = Integer.MAX_VALUE;
}
