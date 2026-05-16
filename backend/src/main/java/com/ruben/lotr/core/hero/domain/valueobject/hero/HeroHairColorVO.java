package com.ruben.lotr.core.hero.domain.valueobject.hero;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public class HeroHairColorVO extends StringValueObject {

    private HeroHairColorVO(String value) {
        super(value);
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    public static @NonNull HeroHairColorVO create(String value) {
        return new HeroHairColorVO(value);
    }

    public static @NonNull HeroHairColorVO unknown() {
        return create("Unknown.");
    }

    @Override
    protected int minLength() {
        return 0;
    }
}
