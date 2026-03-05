package com.ruben.lotr.core.hero.domain.valueobject.hero;

public final class HeroLastNameVOMother {

    private HeroLastNameVOMother() {
    }

    public static HeroLastNameVO defaultLastName() {
        return HeroLastNameVO.create("Son of Arathorn");
    }

    public static HeroLastNameVO create(String lastName) {
        return HeroLastNameVO.create(lastName);
    }
}
