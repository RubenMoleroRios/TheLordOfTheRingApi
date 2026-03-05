package com.ruben.lotr.core.hero.infrastructure.hibernate.entities;

import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideIdVO;
import com.ruben.lotr.core.hero.infrastructure.hibernate.HeroEntityMapper;
import com.ruben.lotr.core.hero.infrastructure.hibernate.HibernateHeroesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@DataJpaTest
@ContextConfiguration(classes = HibernateHeroesRepositoryIT.TestApplication.class)
class HibernateHeroesRepositoryIT {

    @Autowired
    private TestEntityManager testEntityManager;

    private HibernateHeroesRepository repository;

    @BeforeEach
    void setUp() {
        repository = new HibernateHeroesRepository(
                testEntityManager.getEntityManager(),
                new HeroEntityMapper());
    }

    @Test
    void findAll_should_return_heroes_from_database_mapped_to_domain() {
        HeroEntity persisted = persistHero("Frodo", "Baggins");

        List<Hero> heroes = repository.findAll();

        assertNotNull(heroes);
        assertEquals(1, heroes.size());
        assertEquals(persisted.getId().toString(), heroes.get(0).id().value());
        assertEquals("Frodo", heroes.get(0).name().value());
        assertEquals("Baggins", heroes.get(0).lastName().value());
    }

    @Test
    void findById_should_return_hero_when_present() {
        HeroEntity persisted = persistHero("Aragorn", "Elessar");

        Optional<Hero> hero = repository.findById(HeroIdVO.create(persisted.getId().toString()));

        assertTrue(hero.isPresent());
        assertEquals(persisted.getId().toString(), hero.get().id().value());
        assertEquals("Aragorn", hero.get().name().value());
    }

    @Test
    void searchByBreedId_should_filter_by_breed() {
        HeroEntity hero1 = persistHeroWithNewBreed("Legolas", "Greenleaf", "Elf");
        BreedIdVO hero1BreedId = BreedIdVO.create(hero1.getBreed().getId().toString());

        persistHeroWithNewBreed("Gimli", "SonOfGloin", "Dwarf");

        List<Hero> result = repository.searchByBreedId(hero1BreedId);

        assertEquals(1, result.size());
        assertEquals("Legolas", result.get(0).name().value());
    }

    @Test
    void searchBySideId_should_filter_by_side() {
        HeroEntity hero1 = persistHeroWithNewSide("Samwise", "Gamgee", "Free Peoples");
        SideIdVO hero1SideId = SideIdVO.create(hero1.getSide().getId().toString());

        persistHeroWithNewSide("Sauron", "", "Darkness");

        List<Hero> result = repository.searchBySideId(hero1SideId);

        assertEquals(1, result.size());
        assertEquals("Samwise", result.get(0).name().value());
    }

    private HeroEntity persistHero(String name, String lastName) {
        return persistHeroWithNewBreedAndSide(name, lastName, "Hobbit", "Free Peoples");
    }

    private HeroEntity persistHeroWithNewBreed(String name, String lastName, String breedName) {
        return persistHeroWithNewBreedAndSide(name, lastName, breedName, "Free Peoples");
    }

    private HeroEntity persistHeroWithNewSide(String name, String lastName, String sideName) {
        return persistHeroWithNewBreedAndSide(name, lastName, "Maia", sideName);
    }

    private HeroEntity persistHeroWithNewBreedAndSide(String name, String lastName, String breedName, String sideName) {
        BreedEntity breed = new BreedEntity();
        breed.setId(UUID.randomUUID());
        breed.setName(breedName);

        SideEntity side = new SideEntity();
        side.setId(UUID.randomUUID());
        side.setName(sideName);

        testEntityManager.persist(breed);
        testEntityManager.persist(side);

        HeroEntity hero = new HeroEntity();
        hero.setId(UUID.randomUUID());
        hero.setName(name);
        hero.setLastName(lastName);
        hero.setEyesColor(null);
        hero.setHairColor("Brown");
        hero.setHeight(1.10);
        hero.setDescription("");
        hero.setBreed(breed);
        hero.setSide(side);

        testEntityManager.persist(hero);
        testEntityManager.flush();
        testEntityManager.clear();

        return hero;
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @EntityScan(basePackages = {
            "com.ruben.lotr.core.hero.infrastructure.hibernate.entities"
    })
    static class TestApplication {
    }
}
