package com.ruben.lotr.core.auth.application.mapper;

import com.ruben.lotr.core.auth.application.dto.AuthResponse;
import com.ruben.lotr.core.auth.application.dto.UserResponse;
import com.ruben.lotr.core.auth.domain.model.User;

public final class AuthResponseMapper {

    private AuthResponseMapper() {
    }

    public static AuthResponse from(User user, String token) {
        return new AuthResponse(
                token,
                new UserResponse(
                        user.id().value(),
                        user.name().value(),
                        user.email().value()));
    }
}