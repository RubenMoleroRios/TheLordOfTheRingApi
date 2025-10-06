package com.ruben.lotr.thelordofthering_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.thelordofthering_api.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.use_cases.GetHeroeByIdUseCase;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping
public class GetHeroeByIdController {

    @Autowired
    private GetHeroeByIdUseCase getHeroeByIdUseCase;

    @GetMapping("/heroes/{id}")
    public HeroDTO execute(@PathVariable Long id) {
        return getHeroeByIdUseCase.execute(id);
    }

}
