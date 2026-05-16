package com.ruben.lotr.core.hero.domain.valueobject.side;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.shared.domain.valueobject.UuidValueObject;

public class SideIdVO extends UuidValueObject {

    private SideIdVO(String value) {
        super(value);
    }

    public static @NonNull SideIdVO generate() {
        return UuidValueObject.generate(SideIdVO::new);
    }

    public static @NonNull SideIdVO create(String value) {
        return new SideIdVO(value);
    }

    public static @NonNull SideIdVO unknown() {
        return create("00000000-0000-0000-0000-000000000000");
    }
}
