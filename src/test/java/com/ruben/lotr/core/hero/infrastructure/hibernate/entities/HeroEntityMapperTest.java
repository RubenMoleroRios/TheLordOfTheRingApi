package com.ruben.lotr.core.hero.infrastructure.hibernate.entities;

import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.infrastructure.hibernate.HeroEntityMapper;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class HeroEntityMapperTest {

    @Test
    void should_map_hero_entity_to_domain() {
        // Arrange
        HeroEntityMapper mapper = new HeroEntityMapper();

        UUID heroId = UUID.randomUUID();
        UUID breedId = UUID.randomUUID();
        UUID sideId = UUID.randomUUID();

        BreedEntity breed = new BreedEntity();
        breed.setId(breedId);
        breed.setName("Human");

        SideEntity side = new SideEntity();
        side.setId(sideId);
        side.setName("Good");

        HeroEntity entity = new HeroEntity();
        entity.setId(heroId);
        entity.setName("Aragorn");
        entity.setLastName("Elessar");
        entity.setEyesColor("Blue");
        entity.setHairColor("Brown");
        entity.setHeight(1.8);
        entity.setDescription("Heir of Isildur");
        entity.setBreed(breed);
        entity.setSide(side);

        // Act
        Hero hero = mapper.toDomain(entity);

        // Assert
        assertNotNull(hero);
        assertEquals(heroId.toString(), hero.id().value());
        assertEquals("Aragorn", hero.name().value());
        assertEquals("Elessar", hero.lastName().value());
        assertEquals("Blue", hero.eyesColor().value());
        assertEquals("Brown", hero.hairColor().value());
        assertEquals(1.8, hero.height().value(), 0.0001);
        assertEquals("Heir of Isildur", hero.description().value());

        assertEquals(breedId.toString(), hero.breed().id().value());
        assertEquals("Human", hero.breed().name().value());

        assertEquals(sideId.toString(), hero.side().id().value());
        assertEquals("Good", hero.side().name().value());
    }
}
