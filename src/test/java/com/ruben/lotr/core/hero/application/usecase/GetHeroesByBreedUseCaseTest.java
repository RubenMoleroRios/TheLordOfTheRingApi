package com.ruben.lotr.core.hero.application.usecase;

import com.ruben.lotr.core.hero.domain.exception.BreedNotFoundException;
import com.ruben.lotr.core.hero.domain.model.Breed;
import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedNameVO;
import com.ruben.lotr.core.hero.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.hero.domain.model.HeroMother;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@Tag("unit")
public class GetHeroesByBreedUseCaseTest {

    private HeroesRepositoryInterface heroesRepository;
    private GetHeroesByBreedUseCase getHeroesByBreedUseCase;

    @BeforeEach
    void setUp() {
        heroesRepository = mock(HeroesRepositoryInterface.class);
        getHeroesByBreedUseCase = new GetHeroesByBreedUseCase(heroesRepository);
    }

    @Test
    void should_return_heroes_when_breed_exists() {
        // Arrange
        BreedIdVO breedId = BreedIdVO.generate();
        Breed breed = Breed.create(BreedIdVO.create(breedId.value()), BreedNameVO.create("Human"));
        Hero h1 = HeroMother.create(null, breed, null, null, null, null, null, null, null);
        List<Hero> heroes = Arrays.asList(h1);

        when(heroesRepository.searchByBreedId(any(BreedIdVO.class))).thenReturn(heroes);

        // Act
        List<Hero> result = getHeroesByBreedUseCase.execute(breedId.value());

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(heroesRepository).searchByBreedId(argThat(vo -> vo.value().equals(breedId.value())));
    }

    @Test
    void should_throw_exception_when_breed_not_found() {
        // Arrange
        BreedIdVO breedId = BreedIdVO.generate();

        when(heroesRepository.searchByBreedId(any(BreedIdVO.class))).thenReturn(Arrays.asList());

        // Act + Assert
        assertThrows(BreedNotFoundException.class, () -> getHeroesByBreedUseCase.execute(breedId.value()));

        verify(heroesRepository).searchByBreedId(argThat(vo -> vo.value().equals(breedId.value())));
    }
}
