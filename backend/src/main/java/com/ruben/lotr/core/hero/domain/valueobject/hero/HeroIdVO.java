package com.ruben.lotr.core.hero.domain.valueobject.hero;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.shared.domain.valueobject.UuidValueObject;

public class HeroIdVO extends UuidValueObject {

    private HeroIdVO(String value) {
        super(value);
    }

    public static @NonNull HeroIdVO create(String value) {
        return new HeroIdVO(value);
    }

    public static @NonNull HeroIdVO generate() {
        return generate(HeroIdVO::new);
    }
}
