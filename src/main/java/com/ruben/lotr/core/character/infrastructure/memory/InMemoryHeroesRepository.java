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
                                SideIdVO.create("2f4d6b8e-4f2a-4e3a-9f8d-3b1f6e9c5a21"),
                                SideNameVO.create("Good"));

                Side evil = Side.create(
                                SideIdVO.create("9c7a1f43-6d8b-4c2e-b91f-4e3a6d5b2f88"),
                                SideNameVO.create("Evil"));

                Breed human = Breed.create(
                                BreedIdVO.create("6a1b9c4e-3f8d-4c2a-b7e1-5d9f3a2c8e41"),
                                BreedNameVO.create("Human"));

                Breed elf = Breed.create(
                                BreedIdVO.create("d4f8b2c9-1a6e-4e3b-9f72-8c5a3e2b6d91"),
                                BreedNameVO.create("Elf"));

                Breed maia = Breed.create(
                                BreedIdVO.create("a7c3d9f2-8b6e-4a1c-9e5f-2d8b6c4a1f73"),
                                BreedNameVO.create("Maia"));

                Breed dwarf = Breed.create(
                                BreedIdVO.create("f1c8a9b6-4d3e-4f7a-9c2b-8e5d6a3f1b24"),
                                BreedNameVO.create("Dwarf"));

                this.heroes = List.of(

                                Hero.create(
                                                HeroIdVO.create("b3e2c9d1-5a6f-4e8c-9f2a-7d1b6c8a3e45"),
                                                human,
                                                good,
                                                HeroNameVO.create("Aragorn"),
                                                HeroLastNameVO.create("Elessar"),
                                                HeroEyesColorVO.create("Blue"),
                                                HeroHairColorVO.create("Brown"),
                                                HeroHeightVO.create(1.80),
                                                HeroDescriptionVO.create("Heir of Isildur")),

                                Hero.create(
                                                HeroIdVO.create("8a6c3d2e-1f9b-4e7d-a5c8-9b2d6f1e4c73"),
                                                elf,
                                                good,
                                                HeroNameVO.create("Legolas"),
                                                HeroLastNameVO.unknown(),
                                                HeroEyesColorVO.create("Blue"),
                                                HeroHairColorVO.create("Blonde"),
                                                HeroHeightVO.create(1.90),
                                                HeroDescriptionVO.create("Prince of the Woodland Realm")),

                                Hero.create(
                                                HeroIdVO.create("4f9e8b2d-7c6a-4d1e-b5f3-9a2c8e6d1f54"),
                                                maia,
                                                good,
                                                HeroNameVO.create("Gandalf"),
                                                HeroLastNameVO.unknown(),
                                                HeroEyesColorVO.unknown(),
                                                HeroHairColorVO.create("White"),
                                                HeroHeightVO.unknown(),
                                                HeroDescriptionVO.create("Wizard of the Istari order")),

                                Hero.create(
                                                HeroIdVO.create("e7c4a9b2-6f1d-4e8a-9b5c-3d2f6a1c8e90"),
                                                dwarf,
                                                good,
                                                HeroNameVO.create("Gimli"),
                                                HeroLastNameVO.create("Son of Glóin"),
                                                HeroEyesColorVO.create("Brown"),
                                                HeroHairColorVO.create("Red"),
                                                HeroHeightVO.create(1.40),
                                                HeroDescriptionVO.create("Warrior of the Lonely Mountain")),

                                Hero.create(
                                                HeroIdVO.create("9d2f6c8a-1e5b-4c7f-a3e8-6b1d9c2f4a70"),
                                                maia,
                                                evil,
                                                HeroNameVO.create("Sauron"),
                                                HeroLastNameVO.unknown(),
                                                HeroEyesColorVO.unknown(),
                                                HeroHairColorVO.unknown(),
                                                HeroHeightVO.unknown(),
                                                HeroDescriptionVO.create("The Dark Lord")),

                                Hero.create(
                                                HeroIdVO.create("1c9f8a6d-3b2e-4f5a-9d7c-8e6b1a2f4c50"),
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
