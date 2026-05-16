package com.ruben.lotr.core.hero.application.usecase;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.hero.domain.exception.HeroNotFoundException;
import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroIdVO;

@Service
public class GetHeroByIdUseCase {

    private HeroesRepositoryInterface heroesRepository;

    public GetHeroByIdUseCase(HeroesRepositoryInterface heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public Hero execute(String id) throws HeroNotFoundException {

        HeroIdVO heroIdVO = HeroIdVO.create(id);
        Hero hero = heroesRepository.findById(heroIdVO).orElseThrow(() -> new HeroNotFoundException(id));

        return hero;
    }

}
