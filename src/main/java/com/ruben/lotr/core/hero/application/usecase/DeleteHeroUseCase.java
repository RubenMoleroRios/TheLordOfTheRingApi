package com.ruben.lotr.core.hero.application.usecase;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.hero.domain.exception.HeroNotFoundException;
import com.ruben.lotr.core.hero.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroIdVO;

@Service
public class DeleteHeroUseCase {

    private final HeroesRepositoryInterface heroesRepository;

    public DeleteHeroUseCase(HeroesRepositoryInterface heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public void execute(String id) {
        HeroIdVO heroId = HeroIdVO.create(id);
        heroesRepository.findById(heroId)
                .orElseThrow(() -> new HeroNotFoundException(id));
        heroesRepository.deleteById(heroId);
    }
}