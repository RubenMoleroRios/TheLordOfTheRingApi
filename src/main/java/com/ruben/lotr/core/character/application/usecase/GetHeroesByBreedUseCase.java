package com.ruben.lotr.core.character.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.character.domain.exception.BreedNotFoundException;
import com.ruben.lotr.core.character.domain.model.Hero;
import com.ruben.lotr.core.character.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.character.domain.valueobject.BreedIdVO;
import com.ruben.lotr.core.character.application.response.dto.HeroDTO;
import com.ruben.lotr.core.character.application.response.mapper.HeroResponseMapper;

@Service
public class GetHeroesByBreedUseCase {

    private final HeroesRepositoryInterface heroesRepository;
    private final HeroResponseMapper heroResponseMapper;

    public GetHeroesByBreedUseCase(
            HeroesRepositoryInterface heroesRepository,
            HeroResponseMapper heroResponseMapper) {
        this.heroesRepository = heroesRepository;
        this.heroResponseMapper = heroResponseMapper;
    }

    public List<HeroDTO> execute(String breedId) throws BreedNotFoundException {

        BreedIdVO breedIdVO = BreedIdVO.create(breedId);
        List<Hero> heroes = heroesRepository.searchByBreedId(breedIdVO);
        if (heroes == null || heroes.isEmpty()) {
            throw new BreedNotFoundException(breedId);
        }
        return heroResponseMapper.mapMany(heroes);
    }

}
