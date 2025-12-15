package com.ruben.lotr.thelordofthering_api.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.thelordofthering_api.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.http.ApiResponse;
import com.ruben.lotr.thelordofthering_api.http.HttpStatusEnum;
import com.ruben.lotr.thelordofthering_api.use_cases.GetHeroeByIdUseCase;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping
public class GetHeroeByIdController {

    @Autowired
    private GetHeroeByIdUseCase getHeroeByIdUseCase;

    @GetMapping("/heroes/{id}")
    public ResponseEntity<Map<String, Object>> execute(@PathVariable Long id) {
        HeroDTO heroe = getHeroeByIdUseCase.execute(id);

        return ApiResponse.success(
                HttpStatusEnum.OK,
                heroe,
                "Héroe encontrado exitosamente");
    }

}
