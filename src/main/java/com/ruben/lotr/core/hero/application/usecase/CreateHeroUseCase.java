package com.ruben.lotr.core.hero.application.usecase;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.hero.domain.model.Breed;
import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.model.Side;
import com.ruben.lotr.core.hero.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedNameVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroDescriptionVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroEyesColorVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroHairColorVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroHeightVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroLastNameVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroNameVO;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideNameVO;

@Service
public class CreateHeroUseCase {

    private final HeroesRepositoryInterface heroesRepository;

    public CreateHeroUseCase(HeroesRepositoryInterface heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public Hero execute(AdminCreateHeroCommand command) {
        Hero hero = Hero.create(
                Breed.create(BreedIdVO.create(command.breedId()), BreedNameVO.unknown()),
                Side.create(SideIdVO.create(command.sideId()), SideNameVO.unknown()),
                HeroNameVO.create(command.name()),
                HeroLastNameVO.create(command.lastName()),
                HeroEyesColorVO.create(command.eyesColor()),
                HeroHairColorVO.create(command.hairColor()),
                HeroHeightVO.create(command.height()),
                HeroDescriptionVO.create(command.description()));

        return heroesRepository.save(hero);
    }
}