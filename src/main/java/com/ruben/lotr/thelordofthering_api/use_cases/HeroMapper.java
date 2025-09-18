package com.ruben.lotr.thelordofthering_api.use_cases;

import com.ruben.lotr.thelordofthering_api.models.Hero;
import com.ruben.lotr.thelordofthering_api.entities.BreedEntity;
import com.ruben.lotr.thelordofthering_api.entities.HeroEntity;
import com.ruben.lotr.thelordofthering_api.entities.SideEntity;

public class HeroMapper {

    public static HeroEntity toEntity(Hero hero) {
        BreedEntity breedEntity = new BreedEntity(hero.getBreed().getId(), hero.getBreed().getName());
        SideEntity sideEntity = new SideEntity(hero.getSide().getId(), hero.getSide().getName());

        return new HeroEntity(
                hero.getId(),
                hero.getName(),
                hero.getLastName(),
                hero.getEyesColor(),
                hero.getHairColor(),
                hero.getHeight(),
                hero.getDescription(),
                breedEntity,
                sideEntity);
    }

    public static Hero toModel(HeroEntity entity) {
        return new Hero(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getBreed().getId(),
                entity.getSide().getId(),
                entity.getEyesColor(),
                entity.getHairColor(),
                entity.getHeight(),
                entity.getDescription());
    }

}