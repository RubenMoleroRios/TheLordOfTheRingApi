package com.ruben.lotr.core.auth.domain.valueobject;

import org.springframework.lang.NonNull;
import java.util.UUID;

public final class UserEmailVOMother {
    private UserEmailVOMother() {
    }

    public static @NonNull UserEmailVO frodo() {
        return UserEmailVO.create("frodo@mail.com");
    }

    /** Devuelve un email aleatorio como String (útil para withEmail(String)). */
    public static @NonNull String randomEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
    }

    /** Devuelve un UserEmailVO aleatorio (útil para withEmail(UserEmailVO)). */
    public static @NonNull UserEmailVO random() {
        return UserEmailVO.create(randomEmail());
    }
}