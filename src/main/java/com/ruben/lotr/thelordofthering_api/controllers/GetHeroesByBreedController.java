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
import com.ruben.lotr.thelordofthering_api.use_cases.GetHeroesByBreedUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping
public class GetHeroesByBreedController {

    @Autowired
    private GetHeroesByBreedUseCase getHeroesByBreedUseCase;

    @GetMapping("/breeds/{breedId}/heroes")
    public ResponseEntity<Map<String, Object>> execute(@PathVariable Long breedId) {
        List<HeroDTO> heroes = getHeroesByBreedUseCase.execute(breedId);
        return ApiResponse.success(
                HttpStatusEnum.OK,
                heroes,
                "Héroes encontrados exitosamente");
    }
}
