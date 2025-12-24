package com.example.eventservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // ✅ endpoints publics (lecture)
                        .requestMatchers(
                                "/events/**",
                                "/categories/**"
                        ).permitAll()

                        // 🔒 le reste sécurisé
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
