package com.ruben.lotr.api.controllers.v1.hero;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.api.dto.response.CatalogItemResponseDTO;
import com.ruben.lotr.api.http.ApiResponse;
import com.ruben.lotr.api.http.HttpStatusEnum;
import com.ruben.lotr.core.hero.infrastructure.hibernate.CatalogQueryRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/sides")
@Tag(name = "Heroes", description = "Consulta de heroes del universo LOTR")
public class GetSidesController {

    private final CatalogQueryRepository catalogQueryRepository;

    public GetSidesController(CatalogQueryRepository catalogQueryRepository) {
        this.catalogQueryRepository = catalogQueryRepository;
    }

    @GetMapping
    @Operation(summary = "Listar bandos", description = "Devuelve el catalogo de bandos disponible.", security = @SecurityRequirement(name = "bearerAuth"))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Bandos obtenidos correctamente", content = @Content(schema = @Schema(implementation = CatalogItemResponseDTO.class)))
    public ResponseEntity<Map<String, Object>> execute() {
        List<CatalogItemResponseDTO> response = catalogQueryRepository.findAllSides();

        return ApiResponse.success(
                HttpStatusEnum.OK,
                response,
                "Sides successfully retrieved.");
    }
}