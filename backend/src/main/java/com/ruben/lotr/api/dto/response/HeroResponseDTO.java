package com.ruben.lotr.api.dto.response;

import com.ruben.lotr.core.hero.domain.model.Hero;

import io.swagger.v3.oas.annotations.media.Schema;

public record HeroResponseDTO(
                @Schema(description = "Identificador del heroe", example = "1") String id,
                @Schema(description = "Nombre del heroe", example = "Frodo") String name,
                @Schema(description = "Apellido del heroe", example = "Baggins") String lastName,
                @Schema(description = "Raza del heroe", example = "Hobbit") String breedName,
                @Schema(description = "Bando del heroe", example = "Fellowship") String sideName,
                @Schema(description = "Color de ojos", example = "Blue") String eyesColor,
                @Schema(description = "Color de pelo", example = "Brown") String hairColor,
                @Schema(description = "Altura del heroe", example = "1.22") Double height,
                @Schema(description = "Descripcion del heroe", example = "Ring bearer from the Shire") String description) {

        public static HeroResponseDTO from(Hero hero) {
                return new HeroResponseDTO(
                                hero.id() != null ? hero.id().value() : null,
                                hero.name() != null ? hero.name().value() : null,
                                hero.lastName() != null ? hero.lastName().value() : null,
                                hero.breed() != null && hero.breed().name() != null
                                                ? hero.breed().name().value()
                                                : null,
                                hero.side() != null && hero.side().name() != null
                                                ? hero.side().name().value()
                                                : null,
                                hero.eyesColor() != null ? hero.eyesColor().value() : null,
                                hero.hairColor() != null ? hero.hairColor().value() : null,
                                hero.height() != null ? hero.height().value() : null,
                                hero.description() != null ? hero.description().value() : null);
        }
}
