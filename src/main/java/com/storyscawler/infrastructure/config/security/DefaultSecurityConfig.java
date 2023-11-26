package com.storyscawler.infrastructure.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Bean
    @Order(1)
    SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/admin/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry.anyRequest()
                            .hasAnyRole("Admin");
                })
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .userDetailsService(inMemoryUserDetailsManager)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(new BasicAuthenticationEntryPoint())
                );
        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
