package com.ruben.lotr.api.controllers.v1.auth;

public record RegisterRequest(
                String name,
                String email,
                String password) {
}
