package com.ruben.lotr.core.character.domain.valueobject;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public class HeroLastNameVO extends StringValueObject {
    public HeroLastNameVO(String value) {
        super(value);
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    public static HeroLastNameVO create(String value) {
        return new HeroLastNameVO(value);
    }

    public static HeroLastNameVO empty() {
        return create("");
    }

    public static HeroLastNameVO unknown() {
        return create("Unknown.");
    }

    @Override
    protected int minLength() {
        return 0;
    }
}
