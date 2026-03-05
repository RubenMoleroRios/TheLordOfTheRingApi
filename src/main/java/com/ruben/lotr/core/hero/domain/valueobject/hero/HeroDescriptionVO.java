package com.ruben.lotr.core.hero.domain.valueobject.hero;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public class HeroDescriptionVO extends StringValueObject {

    private HeroDescriptionVO(String value) {
        super(value);
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    public static @NonNull HeroDescriptionVO create(String value) {
        return new HeroDescriptionVO(value);
    }

    public static @NonNull HeroDescriptionVO empty() {
        return create("");
    }

    public static HeroDescriptionVO unknown() {
        return create("Unknown. ");
    }

    @Override
    protected int minLength() {
        return 0;
    }

    @Override
    protected int maxLength() {
        return 600;
    }
}
