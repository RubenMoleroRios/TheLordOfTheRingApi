package com.ruben.lotr.thelordofthering_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.thelordofthering_api.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.use_cases.GetHeroesBySideUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping

public class GetHeroesBySideController {

    @Autowired
    private GetHeroesBySideUseCase getHeroesBySideUseCase;

    @GetMapping("/sides/{sideId}/heroes")
    public List<HeroDTO> execute(@PathVariable Long sideId) {
        return getHeroesBySideUseCase.execute(sideId);
    }

}
