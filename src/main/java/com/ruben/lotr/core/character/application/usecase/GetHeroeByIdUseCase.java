package com.ruben.lotr.core.character.application.usecase;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.character.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.character.domain.valueobject.HeroIdVO;
import com.ruben.lotr.core.character.domain.exception.HeroNotFoundException;
import com.ruben.lotr.core.character.domain.model.Hero;

@Service
public class GetHeroeByIdUseCase {

    private HeroesRepositoryInterface heroesRepository;

    public GetHeroeByIdUseCase(HeroesRepositoryInterface heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public Hero execute(String id) throws HeroNotFoundException {

        HeroIdVO heroIdVO = HeroIdVO.create(id);
        Hero hero = heroesRepository.findById(heroIdVO).orElseThrow(() -> new HeroNotFoundException(id));

        return hero;
    }

}
