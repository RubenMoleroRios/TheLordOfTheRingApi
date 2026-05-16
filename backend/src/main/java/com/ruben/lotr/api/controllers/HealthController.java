package com.ruben.lotr.api.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.api.docs.HealthResponseDoc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/health")
@Tag(name = "Health", description = "Endpoints de verificacion del estado de la API")
public class HealthController {

    @GetMapping
    @Operation(summary = "Comprobar estado de la API", description = "Devuelve una respuesta simple para validar que la API esta levantada y respondiendo.")
    @ApiResponse(responseCode = "200", description = "API disponible", content = @Content(schema = @Schema(implementation = HealthResponseDoc.class)))
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }
}
