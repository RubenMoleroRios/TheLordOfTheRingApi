package com.ruben.lotr.core.hero.domain.valueobject.hero;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public class HeroEyesColorVO extends StringValueObject {

    private HeroEyesColorVO(String value) {
        super(value);
    }

    @Override
    protected boolean allowNull() {
        return true;
    }

    public static @NonNull HeroEyesColorVO create(String value) {
        return new HeroEyesColorVO(value);
    }

    public static @NonNull HeroEyesColorVO unknown() {
        return create("Unknown.");
    }
}
