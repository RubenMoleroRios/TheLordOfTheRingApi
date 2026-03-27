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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/heroes")
@Tag(name = "Heroes", description = "Consulta de heroes del universo LOTR")
public class GetHeroesController {

    private final GetHeroesUseCase getHeroesUseCase;

    public GetHeroesController(GetHeroesUseCase getHeroesUseCase) {
        this.getHeroesUseCase = getHeroesUseCase;
    }

    @GetMapping
    @Operation(summary = "Listar heroes", description = "Devuelve todos los heroes disponibles.", security = @SecurityRequirement(name = "bearerAuth"))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Heroes obtenidos correctamente", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.HeroListSuccessResponseDoc.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Token ausente o invalido", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.ApiErrorResponseDoc.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.ApiErrorResponseDoc.class)))
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
