package com.ruben.lotr.core.auth.domain.valueobject;

import org.springframework.lang.NonNull;

public final class UserPasswordHashVOMother {
    private UserPasswordHashVOMother() {
    }

    public static @NonNull UserPasswordHashVO defaultHash() {
        return UserPasswordHashVO.fromHash("hashed-password12345");
    }
}