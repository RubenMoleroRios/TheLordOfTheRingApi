package com.ruben.lotr.core.hero.domain.model;

import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroDescriptionVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroEyesColorVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroHairColorVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroHeightVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroLastNameVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroNameVO;

public final class HeroMother {

    private HeroMother() {
    }

    public static Hero create(
            HeroIdVO id,
            Breed breed,
            Side side,
            HeroNameVO name,
            HeroLastNameVO lastName,
            HeroEyesColorVO eyesColor,
            HeroHairColorVO hairColor,
            HeroHeightVO height,
            HeroDescriptionVO description) {

        return Hero.create(
                id != null ? id : HeroIdVO.generate(),
                breed != null ? breed : Breed.unknown(),
                side != null ? side : Side.unknown(),
                name != null ? name : HeroNameVO.create("Aragorn"),
                lastName != null ? lastName : HeroLastNameVO.create("Son of Arathorn"),
                eyesColor != null ? eyesColor : HeroEyesColorVO.unknown(),
                hairColor != null ? hairColor : HeroHairColorVO.unknown(),
                height != null ? height : HeroHeightVO.unknown(),
                description != null ? description : HeroDescriptionVO.empty());
    }
}
