package com.ruben.lotr.api.controllers.v1.auth;

public record LoginRequest(
        String email,
        String password) {
}
