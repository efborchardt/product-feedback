package com.efborchardt.productfeedback.infrastructure.interfaces.rest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/**").permitAll() // Permit all requests for simplicity
                                .anyRequest().authenticated()
                )
                .csrf().disable(); // Disable CSRF for simplicity

        return http.build();
    }
}
