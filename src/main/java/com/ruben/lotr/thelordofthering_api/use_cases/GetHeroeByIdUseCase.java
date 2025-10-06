package com.ruben.lotr.thelordofthering_api.use_cases;

import org.springframework.stereotype.Service;

import com.ruben.lotr.thelordofthering_api.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.repositories.interfaces.HeroesRepositoryInterface;

@Service
public class GetHeroeByIdUseCase {

    private HeroesRepositoryInterface heroesRepository;

    public GetHeroeByIdUseCase(HeroesRepositoryInterface heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public HeroDTO execute(Long id) {

        return heroesRepository.findById(id);
    }

}
