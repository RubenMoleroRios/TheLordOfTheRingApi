package com.ruben.lotr.core.auth.domain.valueobject;

import java.util.UUID;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public final class UserEmailVO extends StringValueObject {

    private UserEmailVO(String value) {
        super(value);
    }

    public static @NonNull UserEmailVO create(String value) {
        return new UserEmailVO(value);
    }

    public static String randomEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
    }

    public static UserEmailVO random() {
        return UserEmailVO.create(randomEmail());
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    @Override
    protected int maxLength() {
        return 120;
    }
}
