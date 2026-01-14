package com.ruben.lotr.api.controllers.v1;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.api.dto.response.HeroResponseDTO;
import com.ruben.lotr.api.http.ApiResponse;
import com.ruben.lotr.api.http.HttpStatusEnum;
import com.ruben.lotr.core.character.application.usecase.GetHeroesBySideUseCase;
import com.ruben.lotr.core.character.domain.model.Hero;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/v1/sides/{sideId}/heroes")
public class GetHeroesBySideController {

    private final GetHeroesBySideUseCase getHeroesBySideUseCase;

    public GetHeroesBySideController(GetHeroesBySideUseCase getHeroesBySideUseCase) {
        this.getHeroesBySideUseCase = getHeroesBySideUseCase;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> execute(@PathVariable String sideId) {
        List<Hero> heroes = getHeroesBySideUseCase.execute(sideId);

        List<HeroResponseDTO> response = heroes.stream()
                .map(HeroResponseDTO::from)
                .toList();
        return ApiResponse.success(
                HttpStatusEnum.OK,
                response,
                "Heroes successfully retrieved.");
    }

}
