package com.ruben.lotr.thelordofthering_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.thelordofthering_api.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.use_cases.GetHeroesUseCase;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping

public class GetHeroesController {

    @Autowired
    private GetHeroesUseCase getHeroesUseCase;

    @GetMapping("/heroes")
    public List<HeroDTO> execute() {
        return getHeroesUseCase.execute();
    }

}
