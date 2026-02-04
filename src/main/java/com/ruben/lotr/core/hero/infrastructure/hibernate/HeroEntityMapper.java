package com.ruben.lotr.core.hero.infrastructure.hibernate;

import org.springframework.stereotype.Component;

import com.ruben.lotr.core.hero.domain.model.*;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedNameVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroDescriptionVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroEyesColorVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroHairColorVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroHeightVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroLastNameVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroNameVO;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideNameVO;
import com.ruben.lotr.core.hero.infrastructure.hibernate.entities.HeroEntity;

@Component
public class HeroEntityMapper {

        public Hero toDomain(HeroEntity entity) {
                return Hero.create(
                                HeroIdVO.create(entity.getId().toString()),
                                Breed.create(
                                                BreedIdVO.create(entity.getBreed().getId().toString()),
                                                BreedNameVO.create(entity.getBreed().getName())),
                                Side.create(
                                                SideIdVO.create(entity.getSide().getId().toString()),
                                                SideNameVO.create(entity.getSide().getName())),
                                HeroNameVO.create(entity.getName()),
                                HeroLastNameVO.create(entity.getLastName()),
                                HeroEyesColorVO.create(entity.getEyesColor()),
                                HeroHairColorVO.create(entity.getHairColor()),
                                HeroHeightVO.create(entity.getHeight()),
                                HeroDescriptionVO.create(entity.getDescription()));
        }
}
