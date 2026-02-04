package com.ruben.lotr.core.hero.domain.valueobject.side;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

public class SideNameVO extends StringValueObject {

    public SideNameVO(String value) {
        super(value);
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    public static SideNameVO create(String value) {
        return new SideNameVO(value);
    }

    public static SideNameVO unknown() {
        return create("Unknown Side Name.");
    }
}
