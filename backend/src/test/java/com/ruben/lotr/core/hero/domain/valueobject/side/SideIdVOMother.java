package com.ruben.lotr.core.hero.domain.valueobject.side;

import org.springframework.lang.NonNull;

public final class SideIdVOMother {

    private SideIdVOMother() {
    }

    public static @NonNull SideIdVO random() {
        return SideIdVO.generate();
    }

    public static @NonNull SideIdVO unknown() {
        return SideIdVO.unknown();
    }

    public static @NonNull SideIdVO of(@NonNull String value) {
        return SideIdVO.create(value);
    }
}
