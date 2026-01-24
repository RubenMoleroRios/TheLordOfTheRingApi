package com.ruben.lotr.core.auth.domain.valueobject;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.shared.domain.valueobject.UuidValueObject;

public final class UserIdVO extends UuidValueObject {

    private UserIdVO(String value) {
        super(value);
    }

    public static @NonNull UserIdVO create(String value) {
        return new UserIdVO(value);
    }

    public static UserIdVO generate() {
        return generate(UserIdVO::new);
    }
}
