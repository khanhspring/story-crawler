package com.storyscawler.infrastructure.property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = ScheduleProperties.PREFIX)
public class ScheduleProperties {
    protected static final String PREFIX = "app.schedules";

    @NotNull
    private Jobs jobsCreation;
    @NotNull
    private Jobs jobsBootstrap;

    @Getter
    @Setter
    @Validated
    public static class Jobs {
        @NotNull
        private Job storiesCrawling;
        @NotNull
        private Job storyCrawling;
        @NotNull
        private Job chaptersCrawling;
    }


    @Getter
    @Setter
    @Validated
    public static class Job {
        @NotBlank
        private String cron;
        private boolean disabled;
    }
}
