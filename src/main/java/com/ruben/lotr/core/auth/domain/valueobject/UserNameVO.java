package com.ruben.lotr.core.auth.domain.valueobject;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public final class UserNameVO extends StringValueObject {

    private static final int MIN = 3;
    private static final int MAX = 50;

    public UserNameVO(String value) {
        super(value);
    }

    public static @NonNull UserNameVO create(String value) {
        return new UserNameVO(value);
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    @Override
    protected int minLength() {
        return MIN;
    }

    @Override
    protected int maxLength() {
        return MAX;
    }
}
