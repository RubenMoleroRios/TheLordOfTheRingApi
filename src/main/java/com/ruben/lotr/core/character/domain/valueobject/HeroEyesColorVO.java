package com.ruben.lotr.core.character.domain.valueobject;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public class HeroEyesColorVO extends StringValueObject {
    public HeroEyesColorVO(String value) {
        super(value);
    }

    @Override
    protected boolean allowNull() {
        return true;
    }

    public static HeroEyesColorVO create(String value) {
        return new HeroEyesColorVO(value);
    }

    public static HeroEyesColorVO unknown() {
        return create("Unknown");
    }
}
