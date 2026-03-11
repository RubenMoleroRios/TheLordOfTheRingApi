package com.ruben.lotr.core.hero.domain.valueobject.breed;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public class BreedNameVO extends StringValueObject {

    private BreedNameVO(String value) {
        super(value);
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    public static @NonNull BreedNameVO create(String value) {
        return new BreedNameVO(value);
    }

    public static @NonNull BreedNameVO unknown() {
        return create("Unknown Breed.");
    }
}
