package com.ruben.lotr.core.character.application.response.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ruben.lotr.core.character.application.response.dto.HeroDTO;
import com.ruben.lotr.core.character.domain.model.Hero;

@Component
public class HeroResponseMapper {

    public HeroDTO map(Hero hero) {
        return new HeroDTO(
                hero.id() != null ? hero.id().value() : null,
                hero.name() != null ? hero.name().value() : null,
                hero.lastName() != null ? hero.lastName().value() : null,
                hero.breed() != null && hero.breed().name() != null ? hero.breed().name().value() : null,
                hero.side() != null && hero.side().name() != null ? hero.side().name().value() : null,
                hero.eyesColor() != null ? hero.eyesColor().value() : null,
                hero.hairColor() != null ? hero.hairColor().value() : null,
                hero.height() != null ? hero.height().value() : null,
                hero.description() != null ? hero.description().value() : null);
    }

    public List<HeroDTO> mapMany(List<Hero> heroes) {
        return heroes.stream().map(this::map).toList();
    }
}