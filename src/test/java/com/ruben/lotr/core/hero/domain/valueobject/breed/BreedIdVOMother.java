package com.ruben.lotr.core.hero.domain.valueobject.breed;

import org.springframework.lang.NonNull;

public final class BreedIdVOMother {

    private BreedIdVOMother() {
    }

    public static @NonNull BreedIdVO random() {
        return BreedIdVO.generate();
    }

    public static @NonNull BreedIdVO unknown() {
        return BreedIdVO.unknown();
    }

    public static @NonNull BreedIdVO of(@NonNull String value) {
        return BreedIdVO.create(value);
    }
}
