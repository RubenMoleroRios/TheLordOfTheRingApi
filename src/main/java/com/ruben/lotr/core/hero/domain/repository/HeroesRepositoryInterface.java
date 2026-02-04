package com.ruben.lotr.core.hero.domain.repository;

import java.util.List;
import java.util.Optional;

import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideIdVO;

public interface HeroesRepositoryInterface {

    public Optional<Hero> findById(HeroIdVO id);

    public List<Hero> searchByBreedId(BreedIdVO breedId);

    public List<Hero> searchBySideId(SideIdVO sideId);

    public List<Hero> findAll();

}
