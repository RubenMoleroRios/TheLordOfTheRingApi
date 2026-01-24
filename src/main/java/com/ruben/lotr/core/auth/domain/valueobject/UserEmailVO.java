package com.ruben.lotr.core.auth.domain.valueobject;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public final class UserEmailVO extends StringValueObject {

    public UserEmailVO(String value) {
        super(value);
    }

    public static @NonNull UserEmailVO create(String value) {
        return new UserEmailVO(value);
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
