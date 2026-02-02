package com.ruben.lotr.core.auth.application.dto;

public record AuthResponse(
        String token,
        UserResponse user) {
}