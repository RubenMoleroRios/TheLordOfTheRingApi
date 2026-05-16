package com.ruben.lotr.core.hero.domain.valueobject.side;

import org.springframework.lang.NonNull;

public final class SideNameVOMother {

    private SideNameVOMother() {
    }

    public static @NonNull SideNameVO good() {
        return SideNameVO.create("Good");
    }

    public static @NonNull SideNameVO unknown() {
        return SideNameVO.unknown();
    }

    public static @NonNull SideNameVO of(@NonNull String value) {
        return SideNameVO.create(value);
    }
}
