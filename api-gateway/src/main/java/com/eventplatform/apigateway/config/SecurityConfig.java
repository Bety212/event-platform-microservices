package com.eventplatform.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange -> exchange
                        // ✅ endpoints publics
                        .pathMatchers(
                                "/event-service/events",
                                "/event-service/events/**",
                                "/auth/**"
                        ).permitAll()

                        // 🔓 pour le moment, tout le reste est autorisé
                        .anyExchange().permitAll()
                );

        return http.build();
    }
}
