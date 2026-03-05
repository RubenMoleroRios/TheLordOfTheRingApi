package com.ruben.lotr.core.auth.domain.valueobject;

import org.springframework.lang.NonNull;

public final class UserIdVOMother {
    private UserIdVOMother() {
    }

    public static @NonNull UserIdVO random() {
        return UserIdVO.generate();
    }
}