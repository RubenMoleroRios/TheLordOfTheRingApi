package com.ruben.lotr.thelordofthering_api.repositories.interfaces;

import java.util.List;

import com.ruben.lotr.thelordofthering_api.dto.HeroDTO;

public interface HeroesRepositoryInterface {

    public HeroDTO findById(Long id);

    public List<HeroDTO> searchByBreedId(Long breed);

    public List<HeroDTO> searchBySideId(Long side);

    public List<HeroDTO> findAll();

}
