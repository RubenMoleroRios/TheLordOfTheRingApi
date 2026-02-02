package com.ruben.lotr.core.auth.application.dto;

public record UserResponse(
        String id,
        String name,
        String email) {
}