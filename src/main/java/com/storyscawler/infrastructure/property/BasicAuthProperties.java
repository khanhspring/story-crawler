package com.storyscawler.infrastructure.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = BasicAuthProperties.PREFIX)
public class BasicAuthProperties {
    public static final String PREFIX = "app.basic-auth";

    private List<BasicAuthUser> users;
}
