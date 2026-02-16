package com.ruben.lotr.api.controllers.v1.hero;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ruben.lotr.api.dto.response.HeroResponseDTO;
import com.ruben.lotr.api.http.ApiResponse;
import com.ruben.lotr.api.http.HttpStatusEnum;
import com.ruben.lotr.core.hero.application.usecase.GetHeroByIdUseCase;
import com.ruben.lotr.core.hero.domain.model.Hero;

@RestController
@RequestMapping("/v1/heroes")
public class GetHeroeByIdController {

    private final GetHeroByIdUseCase getHeroeByIdUseCase;

    public GetHeroeByIdController(GetHeroByIdUseCase getHeroeByIdUseCase) {
        this.getHeroeByIdUseCase = getHeroeByIdUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> execute(@PathVariable String id) {

        Hero hero = getHeroeByIdUseCase.execute(id);

        HeroResponseDTO response = HeroResponseDTO.from(hero);

        return ApiResponse.success(
                HttpStatusEnum.OK,
                response,
                "Heroes successfully retrieved.");
    }
}
