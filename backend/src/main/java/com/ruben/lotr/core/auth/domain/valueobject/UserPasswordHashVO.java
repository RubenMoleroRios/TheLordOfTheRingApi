package com.ruben.lotr.core.auth.domain.valueobject;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public final class UserPasswordHashVO extends StringValueObject {

    private UserPasswordHashVO(String value) {
        super(value);
    }

    public static @NonNull UserPasswordHashVO fromHash(String hash) {
        return new UserPasswordHashVO(hash);
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    @Override
    protected int minLength() {
        return 20;
    }
}
