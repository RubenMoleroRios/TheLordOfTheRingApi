package com.ruben.lotr.core.character.domain.valueobject;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public class HeroNameVO extends StringValueObject {
    public HeroNameVO(String value) {
        super(value);
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    public static HeroNameVO create(String value) {
        return new HeroNameVO(value);
    }

}
