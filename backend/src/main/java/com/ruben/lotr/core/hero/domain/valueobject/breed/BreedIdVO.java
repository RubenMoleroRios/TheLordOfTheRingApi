package com.ruben.lotr.core.hero.domain.valueobject.breed;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.shared.domain.valueobject.UuidValueObject;

public class BreedIdVO extends UuidValueObject {

    private BreedIdVO(String value) {
        super(value);
    }

    public static @NonNull BreedIdVO generate() {
        return UuidValueObject.generate(BreedIdVO::new);
    }

    public static @NonNull BreedIdVO create(String value) {
        return new BreedIdVO(value);
    }

    public static @NonNull BreedIdVO unknown() {
        return create("00000000-0000-0000-0000-000000000000");
    }
}
