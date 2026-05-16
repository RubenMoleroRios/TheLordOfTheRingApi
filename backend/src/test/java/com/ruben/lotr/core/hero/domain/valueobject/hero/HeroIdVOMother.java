package com.ruben.lotr.core.hero.domain.valueobject.hero;

public final class HeroIdVOMother {

    private HeroIdVOMother() {
    }

    public static HeroIdVO random() {
        return HeroIdVO.generate();
    }
}
