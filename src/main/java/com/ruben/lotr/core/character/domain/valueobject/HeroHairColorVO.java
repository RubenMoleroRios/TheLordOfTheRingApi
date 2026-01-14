package com.ruben.lotr.core.character.domain.valueobject;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public class HeroHairColorVO extends StringValueObject {
    public HeroHairColorVO(String value) {
        super(value);
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    public static HeroHairColorVO create(String value) {
        return new HeroHairColorVO(value);
    }

    public static HeroHairColorVO unknown() {
        return create("Unknown.");
    }

    @Override
    protected int minLength() {
        return 0;
    }
}
