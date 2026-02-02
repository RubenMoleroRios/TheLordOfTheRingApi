package com.ruben.lotr.api.controllers.v1.auth;

import com.ruben.lotr.core.auth.application.dto.AuthResponse;

public final class AuthPresenter {

    private AuthPresenter() {
    }

    public static AuthHttpResponse present(AuthResponse auth) {
        return new AuthHttpResponse(
                auth.token(),
                auth.user().name());
    }
}
