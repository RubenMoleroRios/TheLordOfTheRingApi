package com.ruben.lotr.core.hero.infrastructure.memory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideIdVO;

class InMemoryHeroesRepositoryTest {

    private final InMemoryHeroesRepository repository = new InMemoryHeroesRepository();

    @Test
    void findAll_returnsAllHeroes() {
        List<Hero> heroes = repository.findAll();

        assertThat(heroes).hasSize(7);
    }

    @Test
    void findById_returnsHero_whenIdExists() {
        HeroIdVO aragornId = HeroIdVO.create("96e45918-9804-4df0-8fb4-a9c9cfcfeae1");

        assertThat(repository.findById(aragornId)).isPresent();
        assertThat(repository.findById(aragornId).get().name().value()).isEqualTo("Aragorn");
    }

    @Test
    void findById_returnsEmpty_whenIdDoesNotExist() {
        HeroIdVO missingId = HeroIdVO.create("00000000-0000-0000-0000-000000000000");

        assertThat(repository.findById(missingId)).isEmpty();
    }

    @Test
    void searchByBreedId_filtersByBreed() {
        BreedIdVO maia = BreedIdVO.create("76e2686f-431f-40f0-9361-aed97f5b62a5");

        List<Hero> result = repository.searchByBreedId(maia);

        assertThat(result).hasSize(3);
        assertThat(result).allMatch(h -> h.breed().id().equals(maia));
    }

    @Test
    void searchBySideId_filtersBySide() {
        SideIdVO good = SideIdVO.create("ce145f20-c5d3-42d2-89a1-1c9df74061f6");

        List<Hero> result = repository.searchBySideId(good);

        assertThat(result).hasSize(4);
        assertThat(result).allMatch(h -> h.side().id().equals(good));
    }
}
