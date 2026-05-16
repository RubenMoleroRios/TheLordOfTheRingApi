package com.ruben.lotr.core.auth.domain.valueobject;

import org.springframework.lang.NonNull;

public final class UserNameVOMother {
    private UserNameVOMother() {
    }

    public static @NonNull UserNameVO frodo() {
        return UserNameVO.create("Frodo");
    }

    public static @NonNull UserNameVO any() {
        return UserNameVO.create("Sam");
    }
}