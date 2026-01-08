package com.ruben.lotr.core.character.domain.valueobject;

import com.ruben.lotr.core.shared.domain.valueobject.UuidValueObject;

public class SideIdVO extends UuidValueObject {

    protected SideIdVO(String value) {
        super(value);
    }

    public static SideIdVO generate() {
        return UuidValueObject.generate(SideIdVO::new);
    }

    public static SideIdVO create(String value) {
        return new SideIdVO(value);
    }

    public static SideIdVO unknown() {
        return create("00000000-0000-0000-0000-000000000000");
    }
}
