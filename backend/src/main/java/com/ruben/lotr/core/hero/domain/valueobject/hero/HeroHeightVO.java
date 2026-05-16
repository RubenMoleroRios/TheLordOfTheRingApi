package com.ruben.lotr.core.hero.domain.valueobject.hero;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.hero.domain.exception.InvalidHeroHeightException;
import com.ruben.lotr.core.shared.domain.valueobject.DoubleValueObject;

public class HeroHeightVO extends DoubleValueObject {

    private HeroHeightVO(Double value) {
        super(value);
    }

    public static @NonNull HeroHeightVO create(Double value) {
        return new HeroHeightVO(value);
    }

    public static @NonNull HeroHeightVO unknown() {
        return create(null);
    }

    @Override
    protected void validate(Double value) {
        if (value == null) {
            return;
        }

        if (value <= 0) {
            throw new InvalidHeroHeightException(value);
        }
    }
}
