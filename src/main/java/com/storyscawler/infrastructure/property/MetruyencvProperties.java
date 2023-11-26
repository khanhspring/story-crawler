package com.storyscawler.infrastructure.property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Random;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = MetruyencvProperties.PREFIX)
public class MetruyencvProperties {
    protected static final String PREFIX = "app.metruyencv";

    @NotEmpty
    private List<Account> accounts;

    public Account pickRandomAccount() {
        var rand = new Random();
        return accounts.get(rand.nextInt(accounts.size()));
    }

    @Getter
    @Setter
    @Validated
    public static class Account {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }
}
