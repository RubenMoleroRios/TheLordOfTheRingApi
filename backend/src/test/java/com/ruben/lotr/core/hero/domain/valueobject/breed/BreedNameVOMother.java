package com.ruben.lotr.core.hero.domain.valueobject.breed;

import org.springframework.lang.NonNull;

public final class BreedNameVOMother {

    private BreedNameVOMother() {
    }

    public static @NonNull BreedNameVO human() {
        return BreedNameVO.create("Human");
    }

    public static @NonNull BreedNameVO unknown() {
        return BreedNameVO.unknown();
    }

    public static @NonNull BreedNameVO of(@NonNull String value) {
        return BreedNameVO.create(value);
    }
}
