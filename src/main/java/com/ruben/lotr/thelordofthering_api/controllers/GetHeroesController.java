package com.ruben.lotr.thelordofthering_api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.thelordofthering_api.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.http.ApiResponse;
import com.ruben.lotr.thelordofthering_api.http.HttpStatusEnum;
import com.ruben.lotr.thelordofthering_api.use_cases.GetHeroesUseCase;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping

public class GetHeroesController {

    @Autowired
    private GetHeroesUseCase getHeroesUseCase;

    @GetMapping("/heroes")
    public ResponseEntity<Map<String, Object>> execute() {
        List<HeroDTO> heroes = getHeroesUseCase.execute();
        return ApiResponse.success(
                HttpStatusEnum.OK,
                heroes,
                "Héroes encontrados exitosamente");
    }
}
