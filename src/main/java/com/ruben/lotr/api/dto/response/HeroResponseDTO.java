package com.ruben.lotr.api.dto.response;

import com.ruben.lotr.core.character.domain.model.Hero;

public record HeroResponseDTO(
                String id,
                String name,
                String lastName,
                String breedName,
                String sideName,
                String eyesColor,
                String hairColor,
                Double height,
                String description) {

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
