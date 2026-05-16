package com.ruben.lotr.api.dto.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

public record CatalogItemResponseDTO(
        @Schema(description = "Identificador del catalogo", example = "f99fd696-399c-4417-9acb-72e3eeffb1c7") String id,
        @Schema(description = "Nombre legible del catalogo", example = "Elfos") String name) {

    public static CatalogItemResponseDTO from(UUID id, String name) {
        return new CatalogItemResponseDTO(id.toString(), name);
    }
}