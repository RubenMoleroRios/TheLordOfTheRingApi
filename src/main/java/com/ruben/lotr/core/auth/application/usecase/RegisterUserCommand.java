package com.ruben.lotr.core.auth.application.usecase;

public record RegisterUserCommand(
        String name,
        String email,
        String password) {
}
