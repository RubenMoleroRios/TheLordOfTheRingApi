package com.ruben.lotr.core.hero.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.hero.domain.exception.SideNotFoundException;
import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideIdVO;

@Service
public class GetHeroesBySideUseCase {

    private final HeroesRepositoryInterface heroesRepository;

    public GetHeroesBySideUseCase(HeroesRepositoryInterface heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public List<Hero> execute(String sideId) throws SideNotFoundException {

        SideIdVO sideIdVO = SideIdVO.create(sideId);
        List<Hero> heroes = heroesRepository.searchBySideId(sideIdVO);
        if (heroes == null || heroes.isEmpty()) {
            throw new SideNotFoundException(sideId);
        }
        return heroes;
    }

}
