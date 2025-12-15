package com.ruben.lotr.thelordofthering_api.use_cases;

import org.springframework.stereotype.Service;

import com.ruben.lotr.thelordofthering_api.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.exceptions.HeroNotFoundException;
import com.ruben.lotr.thelordofthering_api.repositories.interfaces.HeroesRepositoryInterface;

@Service
public class GetHeroeByIdUseCase {

    private HeroesRepositoryInterface heroesRepository;

    public GetHeroeByIdUseCase(HeroesRepositoryInterface heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public HeroDTO execute(Long id) throws HeroNotFoundException {
        return heroesRepository.findById(id).orElseThrow(() -> new HeroNotFoundException(id));
    }

}
