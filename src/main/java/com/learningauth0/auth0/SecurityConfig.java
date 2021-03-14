package com.learningauth0.auth0;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.http.HttpMethod.*;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers(OPTIONS, "/api/**").permitAll()
                .pathMatchers(GET, "/api/private-scoped").hasAuthority("SCOPE_messages:read")
                .anyExchange().authenticated()
                .and()
                .cors()
                .and()
                .oauth2ResourceServer()
                .jwt();

        return http.build();
    }
}
