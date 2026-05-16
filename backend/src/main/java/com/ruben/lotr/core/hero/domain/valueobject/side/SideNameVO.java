package com.ruben.lotr.core.hero.domain.valueobject.side;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public class SideNameVO extends StringValueObject {

    private SideNameVO(String value) {
        super(value);
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    public static @NonNull SideNameVO create(String value) {
        return new SideNameVO(value);
    }

    public static @NonNull SideNameVO unknown() {
        return create("Unknown Side Name.");
    }
}
