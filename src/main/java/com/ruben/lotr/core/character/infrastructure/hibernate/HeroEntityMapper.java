package com.ruben.lotr.core.character.infrastructure.hibernate;

import org.springframework.stereotype.Component;

import com.ruben.lotr.core.character.domain.model.*;
import com.ruben.lotr.core.character.domain.valueobject.*;
import com.ruben.lotr.core.character.infrastructure.hibernate.entities.HeroEntity;

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
