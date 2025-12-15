package com.ruben.lotr.thelordofthering_api.use_cases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruben.lotr.thelordofthering_api.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.exceptions.SideNotFoundException;
import com.ruben.lotr.thelordofthering_api.repositories.interfaces.HeroesRepositoryInterface;

@Service
public class GetHeroesBySideUseCase {

    private HeroesRepositoryInterface heroesRepository;

    public GetHeroesBySideUseCase(HeroesRepositoryInterface heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public List<HeroDTO> execute(Long sideId) throws SideNotFoundException {
        List<HeroDTO> heroes = heroesRepository.searchBySideId(sideId);
        if (heroes == null || heroes.isEmpty()) {
            throw new SideNotFoundException(sideId);
        }
        return heroes;
    }

}
