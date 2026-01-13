package com.ruben.lotr.core.character.infrastructure.memory;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.ruben.lotr.core.character.domain.model.Breed;
import com.ruben.lotr.core.character.domain.model.Hero;
import com.ruben.lotr.core.character.domain.model.Side;
import com.ruben.lotr.core.character.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.character.domain.valueobject.*;

@Repository
@Profile("in-memory")
public class InMemoryHeroesRepository implements HeroesRepositoryInterface {

        private final List<Hero> heroes;

        public InMemoryHeroesRepository() {

                Side good = Side.create(
                                SideIdVO.create("ce145f20-c5d3-42d2-89a1-1c9df74061f6"),
                                SideNameVO.create("Good"));

                Side evil = Side.create(
                                SideIdVO.create("5471980f-31c9-4a4d-92bc-9d8fa27a031b"),
                                SideNameVO.create("Evil"));

                Side neutral = Side.create(
                                SideIdVO.create("9f273070-5ffe-4910-ac7a-d1c916dfe7bd"),
                                SideNameVO.create("Neutral"));

                Breed human = Breed.create(
                                BreedIdVO.create("4f54a1d6-83b8-468a-996d-6f8c0b9c0330"),
                                BreedNameVO.create("Human"));

                Breed elf = Breed.create(
                                BreedIdVO.create("f99fd696-399c-4417-9acb-72e3eeffb1c7"),
                                BreedNameVO.create("Elf"));

                Breed maia = Breed.create(
                                BreedIdVO.create("76e2686f-431f-40f0-9361-aed97f5b62a5"),
                                BreedNameVO.create("Maia"));

                Breed dwarf = Breed.create(
                                BreedIdVO.create("c593ae12-93b0-4c9a-a09c-3128335d628c"),
                                BreedNameVO.create("Dwarf"));

                Breed ent = Breed.create(
                                BreedIdVO.create("770db63a-59db-4751-81d3-cedfb3934f28"),
                                BreedNameVO.create("Ent"));

                this.heroes = List.of(

                                Hero.create(
                                                HeroIdVO.create("96e45918-9804-4df0-8fb4-a9c9cfcfeae1"),
                                                human,
                                                good,
                                                HeroNameVO.create("Aragorn"),
                                                HeroLastNameVO.create("Elessar"),
                                                HeroEyesColorVO.create("Blue"),
                                                HeroHairColorVO.create("Brown"),
                                                HeroHeightVO.create(1.80),
                                                HeroDescriptionVO.create("Heir of Isildur")),

                                Hero.create(
                                                HeroIdVO.create("54fa1b2e-e785-4e02-bf15-df030428c4b6"),
                                                elf,
                                                good,
                                                HeroNameVO.create("Legolas"),
                                                HeroLastNameVO.unknown(),
                                                HeroEyesColorVO.create("Blue"),
                                                HeroHairColorVO.create("Blonde"),
                                                HeroHeightVO.create(1.90),
                                                HeroDescriptionVO.create("Prince of the Woodland Realm")),

                                Hero.create(
                                                HeroIdVO.create("dec5a206-5f7f-4867-a1c1-5aaaa25c6d60"),
                                                maia,
                                                good,
                                                HeroNameVO.create("Gandalf"),
                                                HeroLastNameVO.unknown(),
                                                HeroEyesColorVO.unknown(),
                                                HeroHairColorVO.create("White"),
                                                HeroHeightVO.unknown(),
                                                HeroDescriptionVO.create("Wizard of the Istari order")),

                                Hero.create(
                                                HeroIdVO.create("edafd4a0-9ad3-4cdd-a9c8-0ca889c79a5e"),
                                                dwarf,
                                                good,
                                                HeroNameVO.create("Gimli"),
                                                HeroLastNameVO.create("Son of Glóin"),
                                                HeroEyesColorVO.create("Brown"),
                                                HeroHairColorVO.create("Red"),
                                                HeroHeightVO.create(1.40),
                                                HeroDescriptionVO.create("Warrior of the Lonely Mountain")),

                                Hero.create(
                                                HeroIdVO.create("770db63a-59db-4751-81d3-cedfb3934f28"),
                                                ent,
                                                neutral,
                                                HeroNameVO.create("Barbol"),
                                                HeroLastNameVO.create("Treebread"),
                                                HeroEyesColorVO.create("Brown"),
                                                HeroHairColorVO.create("Green"),
                                                HeroHeightVO.create(5.0),
                                                HeroDescriptionVO.create("The oldest of the Ents")),

                                Hero.create(
                                                HeroIdVO.create("f53bc7a7-e8ff-47d3-9549-303db25a8b31"),
                                                maia,
                                                evil,
                                                HeroNameVO.create("Sauron"),
                                                HeroLastNameVO.unknown(),
                                                HeroEyesColorVO.unknown(),
                                                HeroHairColorVO.unknown(),
                                                HeroHeightVO.unknown(),
                                                HeroDescriptionVO.create("The Dark Lord")),

                                Hero.create(
                                                HeroIdVO.create("193ece8a-4f61-4037-a750-ce94628e81ec"),
                                                maia,
                                                evil,
                                                HeroNameVO.create("Saruman"),
                                                HeroLastNameVO.unknown(),
                                                HeroEyesColorVO.create("Dark"),
                                                HeroHairColorVO.create("White"),
                                                HeroHeightVO.unknown(),
                                                HeroDescriptionVO.create("Wizard corrupted by power")));
        }

        @Override
        public Optional<Hero> findById(HeroIdVO id) {
                return heroes.stream()
                                .filter(hero -> hero.id().equals(id))
                                .findFirst();
        }

        @Override
        public List<Hero> searchByBreedId(BreedIdVO breedId) {
                return heroes.stream()
                                .filter(hero -> hero.breed().id().equals(breedId))
                                .toList();
        }

        @Override
        public List<Hero> searchBySideId(SideIdVO sideId) {
                return heroes.stream()
                                .filter(hero -> hero.side().id().equals(sideId))
                                .toList();
        }

        @Override
        public List<Hero> findAll() {
                return heroes;
        }
}
