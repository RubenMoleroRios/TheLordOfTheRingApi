package com.ruben.lotr.core.character.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.character.domain.exception.SideNotFoundException;
import com.ruben.lotr.core.character.domain.model.Hero;
import com.ruben.lotr.core.character.application.response.dto.HeroDTO;
import com.ruben.lotr.core.character.application.response.mapper.HeroResponseMapper;
import com.ruben.lotr.core.character.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.character.domain.valueobject.SideIdVO;

@Service
public class GetHeroesBySideUseCase {

    private final HeroesRepositoryInterface heroesRepository;
    private final HeroResponseMapper heroResponseMapper;

    public GetHeroesBySideUseCase(
            HeroesRepositoryInterface heroesRepository,
            HeroResponseMapper heroResponseMapper) {
        this.heroesRepository = heroesRepository;
        this.heroResponseMapper = heroResponseMapper;
    }

    public List<HeroDTO> execute(String sideId) throws SideNotFoundException {

        SideIdVO sideIdVO = SideIdVO.create(sideId);
        List<Hero> heroes = heroesRepository.searchBySideId(sideIdVO);
        if (heroes == null || heroes.isEmpty()) {
            throw new SideNotFoundException(sideId);
        }
        return heroResponseMapper.mapMany(heroes);
    }

}
