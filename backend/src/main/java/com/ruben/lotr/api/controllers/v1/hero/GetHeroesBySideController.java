package com.ruben.lotr.api.controllers.v1.hero;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.api.dto.response.HeroResponseDTO;
import com.ruben.lotr.api.http.ApiResponse;
import com.ruben.lotr.api.http.HttpStatusEnum;
import com.ruben.lotr.core.hero.application.usecase.GetHeroesBySideUseCase;
import com.ruben.lotr.core.hero.domain.model.Hero;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/sides/{sideId}/heroes")
@Tag(name = "Heroes", description = "Consulta de heroes del universo LOTR")
public class GetHeroesBySideController {

    private final GetHeroesBySideUseCase getHeroesBySideUseCase;

    public GetHeroesBySideController(GetHeroesBySideUseCase getHeroesBySideUseCase) {
        this.getHeroesBySideUseCase = getHeroesBySideUseCase;
    }

    @GetMapping
    @Operation(summary = "Listar heroes por bando", description = "Devuelve los heroes filtrados por el bando indicado.", security = @SecurityRequirement(name = "bearerAuth"))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Heroes filtrados correctamente", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.HeroListSuccessResponseDoc.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Token ausente o invalido", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.ApiErrorResponseDoc.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Bando no encontrado", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.ApiErrorResponseDoc.class)))
    public ResponseEntity<Map<String, Object>> execute(
            @Parameter(description = "Identificador del bando", example = "fellowship") @PathVariable String sideId) {
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
