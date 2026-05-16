package com.ruben.lotr.core.hero.domain.valueobject.hero;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public class HeroNameVO extends StringValueObject {

    private HeroNameVO(String value) {
        super(value);
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    public static @NonNull HeroNameVO create(String value) {
        return new HeroNameVO(value);
    }

}
