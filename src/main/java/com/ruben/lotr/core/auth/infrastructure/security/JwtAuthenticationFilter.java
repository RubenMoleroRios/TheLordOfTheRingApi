package com.ruben.lotr.core.auth.infrastructure.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ruben.lotr.core.auth.domain.exception.InvalidCredentialsException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String JWT_EXCEPTION_ATTR = "JWT_EXCEPTION";

    private final JwtTokenGeneratorImpl jwtTokenGenerator;

    public JwtAuthenticationFilter(JwtTokenGeneratorImpl jwtTokenGenerator) {
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        if (!jwtTokenGenerator.validate(token)) {
            request.setAttribute(JWT_EXCEPTION_ATTR, new InvalidCredentialsException());
            request.getRequestDispatcher("/error/auth").forward(request, response);
            return;
        }

        String userId = jwtTokenGenerator.getUserId(token);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null,
                null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
