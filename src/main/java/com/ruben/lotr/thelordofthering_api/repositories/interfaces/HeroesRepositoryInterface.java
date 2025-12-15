package com.ruben.lotr.thelordofthering_api.repositories.interfaces;

import java.util.List;
import java.util.Optional;

import com.ruben.lotr.thelordofthering_api.dto.HeroDTO;

public interface HeroesRepositoryInterface {

    public Optional<HeroDTO> findById(Long id);

    public List<HeroDTO> searchByBreedId(Long breed);

    public List<HeroDTO> searchBySideId(Long side);

    public List<HeroDTO> findAllHeroes();

}
