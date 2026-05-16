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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/breeds/{breedId}/heroes")
@Tag(name = "Heroes", description = "Consulta de heroes del universo LOTR")
public class GetHeroesByBreedController {

    private final GetHeroesByBreedUseCase getHeroesByBreedUseCase;

    public GetHeroesByBreedController(GetHeroesByBreedUseCase getHeroesByBreedUseCase) {
        this.getHeroesByBreedUseCase = getHeroesByBreedUseCase;
    }

    @GetMapping
    @Operation(summary = "Listar heroes por raza", description = "Devuelve los heroes filtrados por la raza indicada.", security = @SecurityRequirement(name = "bearerAuth"))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Heroes filtrados correctamente", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.HeroListSuccessResponseDoc.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Token ausente o invalido", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.ApiErrorResponseDoc.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Raza no encontrada", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.ApiErrorResponseDoc.class)))
    public ResponseEntity<Map<String, Object>> execute(
            @Parameter(description = "Identificador de la raza", example = "elves") @PathVariable String breedId) {

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
