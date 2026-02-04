package com.ruben.lotr.core.hero.domain.valueobject.breed;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public class BreedNameVO extends StringValueObject {

    public BreedNameVO(String value) {
        super(value);
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    public static BreedNameVO create(String value) {
        return new BreedNameVO(value);
    }

    public static BreedNameVO unknown() {
        return create("Unknown Breed.");
    }
}
