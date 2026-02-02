package com.ruben.lotr.api.controllers;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.api.http.ApiResponse;

import java.util.Map;

@RestController
public class AuthErrorController {

    @RequestMapping("/error/auth")
    public ResponseEntity<Map<String, Object>> handleAuthError(HttpServletRequest request) {

        Object ex = request.getAttribute("JWT_EXCEPTION");

        if (ex instanceof Throwable throwable) {
            return ApiResponse.fromException(throwable);
        }

        return ApiResponse.fromException(new RuntimeException("Authentication error"));
    }
}
