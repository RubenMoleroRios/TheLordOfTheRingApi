package com.ruben.lotr.api.controllers.v1.hero;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ruben.lotr.api.dto.response.HeroResponseDTO;
import com.ruben.lotr.api.http.ApiResponse;
import com.ruben.lotr.api.http.HttpStatusEnum;
import com.ruben.lotr.core.hero.application.usecase.GetHeroByIdUseCase;
import com.ruben.lotr.core.hero.domain.model.Hero;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/heroes")
@Tag(name = "Heroes", description = "Consulta de heroes del universo LOTR")
public class GetHeroeByIdController {

    private final GetHeroByIdUseCase getHeroeByIdUseCase;

    public GetHeroeByIdController(GetHeroByIdUseCase getHeroeByIdUseCase) {
        this.getHeroeByIdUseCase = getHeroeByIdUseCase;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener heroe por id", description = "Busca un heroe concreto a partir de su identificador.", security = @SecurityRequirement(name = "bearerAuth"))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Heroe encontrado", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.HeroSuccessResponseDoc.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Heroe no encontrado", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.ApiErrorResponseDoc.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Token ausente o invalido", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.ApiErrorResponseDoc.class)))
    public ResponseEntity<Map<String, Object>> execute(
            @Parameter(description = "Identificador del heroe", example = "1") @PathVariable String id) {

        Hero hero = getHeroeByIdUseCase.execute(id);

        HeroResponseDTO response = HeroResponseDTO.from(hero);

        return ApiResponse.success(
                HttpStatusEnum.OK,
                response,
                "Heroes successfully retrieved.");
    }
}
