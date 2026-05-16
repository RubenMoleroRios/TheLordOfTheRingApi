package com.ruben.lotr.core.hero.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.hero.domain.exception.BreedNotFoundException;
import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;

@Service
public class GetHeroesByBreedUseCase {

    private final HeroesRepositoryInterface heroesRepository;

    public GetHeroesByBreedUseCase(HeroesRepositoryInterface heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public List<Hero> execute(String breedId) throws BreedNotFoundException {

        BreedIdVO breedIdVO = BreedIdVO.create(breedId);
        List<Hero> heroes = heroesRepository.searchByBreedId(breedIdVO);
        if (heroes == null || heroes.isEmpty()) {
            throw new BreedNotFoundException(breedId);
        }
        return heroes;
    }

}
