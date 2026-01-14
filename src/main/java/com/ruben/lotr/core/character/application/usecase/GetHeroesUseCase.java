package com.ruben.lotr.core.character.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.character.domain.model.Hero;
import com.ruben.lotr.core.character.domain.repository.HeroesRepositoryInterface;

@Service

public class GetHeroesUseCase {

    private final HeroesRepositoryInterface heroesRepository;

    public GetHeroesUseCase(HeroesRepositoryInterface heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public List<Hero> execute() {

        List<Hero> heroes = heroesRepository.findAll();

        return heroes;
    }
}
