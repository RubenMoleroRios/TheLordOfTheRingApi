package com.ruben.lotr.api.controllers.v1.hero;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ruben.lotr.api.dto.response.HeroResponseDTO;
import com.ruben.lotr.api.http.ApiResponse;
import com.ruben.lotr.api.http.HttpStatusEnum;
import com.ruben.lotr.core.hero.application.usecase.GetHeroesByBreedUseCase;
import com.ruben.lotr.core.hero.domain.model.Hero;

@RestController
@RequestMapping("/v1/breeds/{breedId}/heroes")
public class GetHeroesByBreedController {

    private final GetHeroesByBreedUseCase getHeroesByBreedUseCase;

    public GetHeroesByBreedController(GetHeroesByBreedUseCase getHeroesByBreedUseCase) {
        this.getHeroesByBreedUseCase = getHeroesByBreedUseCase;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> execute(@PathVariable String breedId) {

        List<Hero> heroes = getHeroesByBreedUseCase.execute(breedId);

        List<HeroResponseDTO> response = heroes.stream()
                .map(HeroResponseDTO::from)
                .toList();

        return ApiResponse.success(
                HttpStatusEnum.OK,
                response,
                "Heroes successfully retrieved.");
    }
}
