package com.ruben.lotr.core.character.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.character.application.response.dto.HeroDTO;
import com.ruben.lotr.core.character.application.response.mapper.HeroResponseMapper;
import com.ruben.lotr.core.character.domain.model.Hero;
import com.ruben.lotr.core.character.domain.repository.HeroesRepositoryInterface;

@Service

public class GetHeroesUseCase {

    private final HeroesRepositoryInterface heroesRepository;
    private final HeroResponseMapper heroResponseMapper;

    public GetHeroesUseCase(
            HeroesRepositoryInterface heroesRepository,
            HeroResponseMapper heroResponseMapper) {
        this.heroesRepository = heroesRepository;
        this.heroResponseMapper = heroResponseMapper;
    }

    public List<HeroDTO> execute() {

        List<Hero> heroes = heroesRepository.findAll();

        return heroResponseMapper.mapMany(heroes);
    }
}
