package com.ruben.lotr.core.auth.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

        private final JwtTokenGeneratorImpl jwtTokenGenerator;
        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

        public SecurityConfig(
                        JwtTokenGeneratorImpl jwtTokenGenerator,
                        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
                this.jwtTokenGenerator = jwtTokenGenerator;
                this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtTokenGenerator);

                http.csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                                .authorizeHttpRequests(auth -> auth
                                                // Public
                                                .requestMatchers(
                                                                "/v1/auth/**",
                                                                "/health")
                                                .permitAll()
                                                // Private
                                                .anyRequest().authenticated())
                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
