package com.ruben.lotr.thelordofthering_api.use_cases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruben.lotr.thelordofthering_api.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.repositories.interfaces.HeroesRepositoryInterface;

@Service
public class GetHeroesUseCase {

    private HeroesRepositoryInterface heroesRepository;

    public GetHeroesUseCase(HeroesRepositoryInterface heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public List<HeroDTO> execute() {
        return heroesRepository.findAllHeroes();
    }

}
