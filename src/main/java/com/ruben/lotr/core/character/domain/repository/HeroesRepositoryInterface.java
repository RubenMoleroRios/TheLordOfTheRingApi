package com.ruben.lotr.core.character.domain.repository;

import java.util.List;
import java.util.Optional;

import com.ruben.lotr.core.character.domain.model.Hero;
import com.ruben.lotr.core.character.domain.valueobject.BreedIdVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroIdVO;
import com.ruben.lotr.core.character.domain.valueobject.SideIdVO;

public interface HeroesRepositoryInterface {

    public Optional<Hero> findById(HeroIdVO id);

    public List<Hero> searchByBreedId(BreedIdVO breedId);

    public List<Hero> searchBySideId(SideIdVO sideId);

    public List<Hero> findAll();

}
