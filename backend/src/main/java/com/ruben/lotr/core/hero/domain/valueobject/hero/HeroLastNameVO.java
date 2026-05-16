package com.ruben.lotr.core.hero.domain.valueobject.hero;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public class HeroLastNameVO extends StringValueObject {

    private HeroLastNameVO(String value) {
        super(value);
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    public static @NonNull HeroLastNameVO create(String value) {
        return new HeroLastNameVO(value);
    }

    public static @NonNull HeroLastNameVO empty() {
        return create("");
    }

    public static @NonNull HeroLastNameVO unknown() {
        return create("Unknown.");
    }

    @Override
    protected int minLength() {
        return 0;
    }
}
