package com.ruben.lotr.core.hero.infrastructure.external.lotr;

import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroIdVO;
import com.ruben.lotr.core.hero.infrastructure.external.lotr.client.LotrHeroResponseApiDTO;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class HeroApiMapperTest {

    @Test
    void should_map_first_and_last_name_when_full_name_has_two_parts() {
        // Arrange
        HeroApiMapper mapper = new HeroApiMapper();
        LotrHeroResponseApiDTO dto = new LotrHeroResponseApiDTO();
        dto.setName("Frodo Baggins");
        dto.setRace("Hobbit");
        dto.setHair("Brown");
        dto.setHeight(null);

        Map<String, String> breedIdMap = Map.of(
                BreedIdVO.create("450e8400-e29b-41d4-a716-446655440001").value(), "Hobbit");

        // Act
        Hero hero = mapper.toDomain(dto, null, breedIdMap);

        // Assert
        assertNotNull(hero);
        assertEquals("Frodo", hero.name().value());
        assertEquals("Baggins", hero.lastName().value());
    }

    @Test
    void should_set_unknown_last_name_when_full_name_has_one_part() {
        // Arrange
        HeroApiMapper mapper = new HeroApiMapper();
        LotrHeroResponseApiDTO dto = new LotrHeroResponseApiDTO();
        dto.setName("Gandalf");
        dto.setRace("Maiar");
        dto.setHair("White");

        Map<String, String> breedIdMap = Map.of(
                BreedIdVO.create("450e8400-e29b-41d4-a716-446655440004").value(), "Maiar");

        // Act
        Hero hero = mapper.toDomain(dto, null, breedIdMap);

        // Assert
        assertNotNull(hero);
        assertEquals("Gandalf", hero.name().value());
        assertEquals("Unknown.", hero.lastName().value());
    }

    @Test
    void should_parse_height_in_cm_to_meters_when_height_is_in_cm() {
        // Arrange
        HeroApiMapper mapper = new HeroApiMapper();
        LotrHeroResponseApiDTO dto = new LotrHeroResponseApiDTO();
        dto.setName("Aragorn Elessar");
        dto.setRace("Human");
        dto.setHair("Brown");
        dto.setHeight("180cm");

        Map<String, String> breedIdMap = Map.of(
                BreedIdVO.create("450e8400-e29b-41d4-a716-446655440000").value(), "Human");

        // Act
        Hero hero = mapper.toDomain(dto, null, breedIdMap);

        // Assert
        assertNotNull(hero.height());
        assertEquals(1.8, hero.height().value(), 0.0001);
    }

    @Test
    void should_parse_height_in_meters_when_height_is_in_m() {
        // Arrange
        HeroApiMapper mapper = new HeroApiMapper();
        LotrHeroResponseApiDTO dto = new LotrHeroResponseApiDTO();
        dto.setName("Legolas");
        dto.setRace("Elf");
        dto.setHair("Blonde");
        dto.setHeight("1.9m");

        Map<String, String> breedIdMap = Map.of(
                BreedIdVO.create("450e8400-e29b-41d4-a716-446655440002").value(), "Elf");

        // Act
        Hero hero = mapper.toDomain(dto, null, breedIdMap);

        // Assert
        assertNotNull(hero.height());
        assertEquals(1.9, hero.height().value(), 0.0001);
    }

    @Test
    void should_map_special_height_text_to_number_when_height_is_very_tall() {
        // Arrange
        HeroApiMapper mapper = new HeroApiMapper();
        LotrHeroResponseApiDTO dto = new LotrHeroResponseApiDTO();
        dto.setName("Treebeard");
        dto.setRace("Ent");
        dto.setHair("");
        dto.setHeight("Very tall");

        Map<String, String> breedIdMap = Map.of(
                BreedIdVO.create("450e8400-e29b-41d4-a716-446655440005").value(), "Ent");

        // Act
        Hero hero = mapper.toDomain(dto, null, breedIdMap);

        // Assert
        assertNotNull(hero.height());
        assertEquals(3.0, hero.height().value(), 0.0001);
    }

    @Test
    void should_keep_hero_id_when_hero_id_is_provided() {
        // Arrange
        HeroApiMapper mapper = new HeroApiMapper();
        LotrHeroResponseApiDTO dto = new LotrHeroResponseApiDTO();
        dto.setName("Frodo Baggins");
        dto.setRace("Hobbit");
        dto.setHair("Brown");

        HeroIdVO heroId = HeroIdVO.generate();

        Map<String, String> breedIdMap = Map.of(
                BreedIdVO.create("450e8400-e29b-41d4-a716-446655440001").value(), "Hobbit");

        // Act
        Hero hero = mapper.toDomain(dto, heroId, breedIdMap);

        // Assert
        assertNotNull(hero.id());
        assertEquals(heroId.value(), hero.id().value());
    }
}
