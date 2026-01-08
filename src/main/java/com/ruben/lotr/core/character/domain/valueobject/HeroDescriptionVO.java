package com.ruben.lotr.core.character.domain.valueobject;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public class HeroDescriptionVO extends StringValueObject {
    public HeroDescriptionVO(String value) {
        super(value);
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    public static HeroDescriptionVO create(String value) {
        return new HeroDescriptionVO(value);
    }

    public static HeroDescriptionVO empty() {
        return create("");
    }

    public static HeroDescriptionVO unknown() {
        return create("Unknown");
    }

    @Override
    protected int minLength() {
        return 0;
    }

    @Override
    protected int maxLength() {
        return 600;
    }
}
