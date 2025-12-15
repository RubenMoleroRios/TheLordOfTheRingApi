package com.ruben.lotr.thelordofthering_api.use_cases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruben.lotr.thelordofthering_api.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.exceptions.BreedNotFoundException;
import com.ruben.lotr.thelordofthering_api.repositories.interfaces.HeroesRepositoryInterface;

@Service
public class GetHeroesByBreedUseCase {

    private HeroesRepositoryInterface heroesRepository;

    public GetHeroesByBreedUseCase(HeroesRepositoryInterface heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public List<HeroDTO> execute(Long breedId) throws BreedNotFoundException {
        List<HeroDTO> heroes = heroesRepository.searchByBreedId(breedId);
        if (heroes == null || heroes.isEmpty()) {
            throw new BreedNotFoundException(breedId);
        }
        return heroes;
    }

}
