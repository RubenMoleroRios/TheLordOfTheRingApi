package com.ruben.lotr.api.controllers.v1.hero;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.api.dto.response.HeroResponseDTO;
import com.ruben.lotr.api.http.ApiResponse;
import com.ruben.lotr.api.http.HttpStatusEnum;
import com.ruben.lotr.core.hero.application.usecase.GetHeroesUseCase;
import com.ruben.lotr.core.hero.domain.model.Hero;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/v1/heroes")
public class GetHeroesController {

    private final GetHeroesUseCase getHeroesUseCase;

    public GetHeroesController(GetHeroesUseCase getHeroesUseCase) {
        this.getHeroesUseCase = getHeroesUseCase;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> execute() {
        List<Hero> heroes = getHeroesUseCase.execute();

        List<HeroResponseDTO> response = heroes.stream()
                .map(HeroResponseDTO::from)
                .toList();
        return ApiResponse.success(
                HttpStatusEnum.OK,
                response,
                "Heroes successfully retrieved.");
    }
}
