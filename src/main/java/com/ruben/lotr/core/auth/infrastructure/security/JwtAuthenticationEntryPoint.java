package com.ruben.lotr.core.auth.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruben.lotr.api.http.ApiResponse;
import com.ruben.lotr.core.auth.domain.exception.MissingTokenException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        ResponseEntity<Map<String, Object>> apiResponse = ApiResponse.fromException(new MissingTokenException());

        response.setStatus(apiResponse.getStatusCode().value());
        response.setContentType("application/json");
        response.getWriter().write(
                objectMapper.writeValueAsString(apiResponse.getBody()));
    }
}