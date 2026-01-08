package com.ruben.lotr.core.character.domain.valueobject;

import com.ruben.lotr.core.shared.domain.valueobject.UuidValueObject;

public class BreedIdVO extends UuidValueObject {

    protected BreedIdVO(String value) {
        super(value);
    }

    public static BreedIdVO generate() {
        return UuidValueObject.generate(BreedIdVO::new);
    }

    public static BreedIdVO create(String value) {
        return new BreedIdVO(value);
    }

    public static BreedIdVO unknown() {
        return create("00000000-0000-0000-0000-000000000000");
    }
}
