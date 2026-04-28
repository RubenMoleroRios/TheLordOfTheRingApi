package com.ruben.lotr.core.auth.infrastructure.security;

import org.springframework.http.HttpMethod;
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
        private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

        public SecurityConfig(
                        JwtTokenGeneratorImpl jwtTokenGenerator,
                        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                        JwtAccessDeniedHandler jwtAccessDeniedHandler) {
                this.jwtTokenGenerator = jwtTokenGenerator;
                this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
                this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtTokenGenerator);

                http.csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                                .accessDeniedHandler(jwtAccessDeniedHandler))
                                .authorizeHttpRequests(auth -> auth
                                                // Public
                                                .requestMatchers(
                                                                "/v3/api-docs/**",
                                                                "/swagger-ui/**",
                                                                "/swagger-ui.html",
                                                                "/v1/auth/login",
                                                                "/v1/auth/register",
                                                                "/health")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.POST, "/v1/auth/logout").authenticated()
                                                .requestMatchers(HttpMethod.GET, "/v1/heroes/**", "/v1/breeds/**",
                                                                "/v1/sides/**")
                                                .hasAuthority("HERO_READ")
                                                .requestMatchers(HttpMethod.GET, "/v1/admin/users", "/v1/admin/users/*")
                                                .hasAuthority("USER_READ")
                                                .requestMatchers(HttpMethod.POST, "/v1/admin/users")
                                                .hasAuthority("USER_CREATE")
                                                .requestMatchers(HttpMethod.PUT, "/v1/admin/users/*")
                                                .hasAuthority("USER_UPDATE")
                                                .requestMatchers(HttpMethod.DELETE, "/v1/admin/users/*")
                                                .hasAuthority("USER_DELETE")
                                                .requestMatchers(HttpMethod.POST, "/v1/admin/heroes")
                                                .hasAuthority("HERO_CREATE")
                                                .requestMatchers(HttpMethod.PUT, "/v1/admin/heroes/*")
                                                .hasAuthority("HERO_UPDATE")
                                                .requestMatchers(HttpMethod.DELETE, "/v1/admin/heroes/*")
                                                .hasAuthority("HERO_DELETE")
                                                .anyRequest().authenticated())
                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
