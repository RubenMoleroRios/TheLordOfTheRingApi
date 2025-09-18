package com.ruben.lotr.thelordofthering_api.repositories.implementations;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.ruben.lotr.thelordofthering_api.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.models.Breed;
import com.ruben.lotr.thelordofthering_api.models.Hero;
import com.ruben.lotr.thelordofthering_api.models.Side;
import com.ruben.lotr.thelordofthering_api.repositories.interfaces.HeroesRepositoryInterface;

@Repository
// @Primary
public class InMemoryHeroesRepository implements HeroesRepositoryInterface {

        private List<Hero> heroList;
        private List<Breed> breedList;
        private List<Side> sideList;

        public InMemoryHeroesRepository() {
                this.heroList = Arrays.asList(
                                new Hero(1L, "Aragorn", "apellido", 1L, 1L, "azul", "cafe", 1.8,
                                                "descripcion"),
                                new Hero(2L, "Legolas", "apellido", 1L, 1L, "azul", "cafe", 1.8,
                                                "descripcion"),
                                new Hero(3L, "Gandalf", "apellido", 1L, 1L, "azul", "cafe", 1.8,
                                                "descripcion"),
                                new Hero(4L, "Gimli", "apellido", 1L, 1L, "azul", "cafe", 1.8,
                                                "descripcion"),
                                new Hero(5L, "Sauron", "apellido", 2L, 1L, "azul", "cafe", 1.8,
                                                "descripcion"),
                                new Hero(6L, "Saruman", "apellido", 2L, 2L, "azul", "cafe", 1.8,
                                                "descripcion"));
                this.breedList = Arrays.asList(
                                new Breed(1L, "Hobbit"),
                                new Breed(2L, "Human"));
                this.sideList = Arrays.asList(
                                new Side(1L, "Bien"),
                                new Side(2L, "Mal"));
        }

        @Override
        public List<HeroDTO> findAll() {
                return heroList.stream()
                                .map(hero -> {
                                        // Buscar la raza correspondiente
                                        String breedName = breedList.stream()
                                                        .filter(breed -> breed.getId().equals(hero.getIdBreed()))
                                                        .findFirst()
                                                        .map(Breed::getName)
                                                        .orElse("Desconocido");

                                        // Buscar el bando correspondiente
                                        String sideName = sideList.stream()
                                                        .filter(side -> side.getId().equals(hero.getIdSide()))
                                                        .findFirst()
                                                        .map(Side::getName)
                                                        .orElse("Desconocido");

                                        // Crear y devolver el DTO con los datos del héroe, raza y bando
                                        return new HeroDTO(
                                                        hero.getId(),
                                                        hero.getName(),
                                                        hero.getLastName(),
                                                        breedName,
                                                        sideName,
                                                        hero.getEyesColor(),
                                                        hero.getHairColor(),
                                                        hero.getHeight(),
                                                        hero.getDescription());
                                })
                                .collect(Collectors.toList());
        }

        public HeroDTO findById(Long id) {
                Hero hero = heroList.stream().filter(h -> h.getId().equals(id)).findFirst().orElseThrow();
                Breed breed = breedList.stream().filter(b -> b.getId().equals(hero.getIdBreed())).findFirst()
                                .orElseThrow();
                Side side = sideList.stream().filter(s -> s.getId().equals(hero.getIdSide())).findFirst()
                                .orElseThrow();

                return new HeroDTO(
                                hero.getId(),
                                hero.getName(),
                                hero.getLastName(),
                                breed.getName(),
                                side.getName(),
                                hero.getEyesColor(),
                                hero.getHairColor(),
                                hero.getHeight(),
                                hero.getDescription());
        }

        @Override
        public List<HeroDTO> searchByBreedId(Long breedId) {
                return heroList.stream()
                                .filter(h -> h.getIdBreed().equals(breedId))
                                .map(hero -> {
                                        String breedName = breedList.stream()
                                                        .filter(breed -> breed.getId().equals(hero.getIdBreed()))
                                                        .findFirst()
                                                        .map(Breed::getName)
                                                        .orElse("Desconocido");

                                        String sideName = sideList.stream()
                                                        .filter(side -> side.getId().equals(hero.getIdSide()))
                                                        .findFirst()
                                                        .map(Side::getName)
                                                        .orElse("Desconocido");

                                        return new HeroDTO(
                                                        hero.getId(),
                                                        hero.getName(),
                                                        hero.getLastName(),
                                                        breedName,
                                                        sideName,
                                                        hero.getEyesColor(),
                                                        hero.getHairColor(),
                                                        hero.getHeight(),
                                                        hero.getDescription());
                                })
                                .collect(Collectors.toList());

        }

        @Override
        public List<HeroDTO> searchBySideId(Long sideId) {
                return heroList.stream()
                                .filter(h -> h.getIdSide().equals(sideId))
                                .map(hero -> {
                                        String breedName = breedList.stream()
                                                        .filter(breed -> breed.getId().equals(hero.getIdBreed()))
                                                        .findFirst()
                                                        .map(Breed::getName)
                                                        .orElse("Desconocido");

                                        String sideName = sideList.stream()
                                                        .filter(side -> side.getId().equals(hero.getIdSide()))
                                                        .findFirst()
                                                        .map(Side::getName)
                                                        .orElse("Desconocido");

                                        return new HeroDTO(
                                                        hero.getId(),
                                                        hero.getName(),
                                                        hero.getLastName(),
                                                        breedName,
                                                        sideName,
                                                        hero.getEyesColor(),
                                                        hero.getHairColor(),
                                                        hero.getHeight(),
                                                        hero.getDescription());
                                })
                                .collect(Collectors.toList());
        }

}
