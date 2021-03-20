package com.learningauth0.auth0;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.http.HttpMethod.*;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${auth0.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers(OPTIONS, "/api/**").permitAll()
                .pathMatchers(GET, "/api/private-scoped").hasAuthority("SCOPE_openid")
                .anyExchange().authenticated()
                .and()
                .cors()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtDecoder(jwtDecoder());

        return http.build();
    }

    ReactiveJwtDecoder jwtDecoder() {
        NimbusReactiveJwtDecoder jwtDecoder = (NimbusReactiveJwtDecoder)
                ReactiveJwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);
        jwtDecoder.setClaimSetConverter(new ClaimAdapter());

        return jwtDecoder;
    }
}
