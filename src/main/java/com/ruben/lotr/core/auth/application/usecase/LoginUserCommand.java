package com.ruben.lotr.core.auth.application.usecase;

public record LoginUserCommand(
        String email,
        String password) {
}
