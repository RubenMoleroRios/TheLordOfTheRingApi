package com.ruben.lotr.core.hero.domain.valueobject.hero;

import com.ruben.lotr.core.shared.domain.valueobject.UuidValueObject;

public class HeroIdVO extends UuidValueObject {

    protected HeroIdVO(String value) {
        super(value);
    }

    public static HeroIdVO create(String value) {
        return new HeroIdVO(value);
    }

    public static HeroIdVO generate() {
        return UuidValueObject.generate(HeroIdVO::new);
    }
}
