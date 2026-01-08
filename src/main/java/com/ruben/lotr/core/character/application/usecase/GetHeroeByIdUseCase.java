package com.ruben.lotr.core.character.application.usecase;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.character.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.character.domain.valueobject.HeroIdVO;
import com.ruben.lotr.core.character.application.response.dto.HeroDTO;
import com.ruben.lotr.core.character.application.response.mapper.HeroResponseMapper;
import com.ruben.lotr.core.character.domain.exception.HeroNotFoundException;
import com.ruben.lotr.core.character.domain.model.Hero;

@Service
public class GetHeroeByIdUseCase {

    private HeroesRepositoryInterface heroesRepository;

    private HeroResponseMapper heroResponseMapper;

    public GetHeroeByIdUseCase(
            HeroesRepositoryInterface heroesRepository,
            HeroResponseMapper heroResponseMapper) {
        this.heroesRepository = heroesRepository;
        this.heroResponseMapper = heroResponseMapper;
    }

    public HeroDTO execute(String id) throws HeroNotFoundException {

        HeroIdVO heroIdVO = HeroIdVO.create(id);
        Hero hero = heroesRepository.findById(heroIdVO).orElseThrow(() -> new HeroNotFoundException(id));

        return heroResponseMapper.map(hero);
    }

}
