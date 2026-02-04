package com.ruben.lotr.core.hero.domain.valueobject.hero;

import com.ruben.lotr.core.hero.domain.exception.InvalidHeroHeightException;
import com.ruben.lotr.core.shared.domain.valueobject.DoubleValueObject;

public class HeroHeightVO extends DoubleValueObject {
    public HeroHeightVO(Double value) {
        super(value);
    }

    public static HeroHeightVO create(Double value) {
        return new HeroHeightVO(value);
    }

    public static HeroHeightVO unknown() {
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
