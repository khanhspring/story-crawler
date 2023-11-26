package com.storyscawler.infrastructure.config.security;

import com.storyscawler.infrastructure.property.BasicAuthProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.util.ObjectUtils;

@Configuration
public class BasicAuthConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(BasicAuthProperties basicAuthProperties) {
        if (ObjectUtils.isEmpty(basicAuthProperties.getUsers())) {
            return new InMemoryUserDetailsManager();
        }

        var userDetails = new InMemoryUserDetailsManager();
        for (var basicAuthUser: basicAuthProperties.getUsers()) {
            UserDetails user = User.builder()
                    .username(basicAuthUser.getUsername())
                    .password(basicAuthUser.getPassword())
                    .roles(basicAuthUser.getRoles().toArray(String[]::new))
                    .build();
            userDetails.createUser(user);
        }
        return userDetails;
    }
}
