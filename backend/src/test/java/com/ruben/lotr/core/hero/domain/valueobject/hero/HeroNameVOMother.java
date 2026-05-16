package com.ruben.lotr.core.hero.domain.valueobject.hero;

public final class HeroNameVOMother {

    private HeroNameVOMother() {
    }

    public static HeroNameVO aragorn() {
        return HeroNameVO.create("Aragorn");
    }

    public static HeroNameVO create(String name) {
        return HeroNameVO.create(name);
    }
}
