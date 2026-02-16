package com.ruben.lotr.core.hero.application.usecase;

import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.hero.domain.model.HeroMother;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("unit")
public class GetHeroesUseCaseTest {

    private HeroesRepositoryInterface heroesRepository;
    private GetHeroesUseCase getHeroesUseCase;

    @BeforeEach
    void setUp() {
        heroesRepository = mock(HeroesRepositoryInterface.class);
        getHeroesUseCase = new GetHeroesUseCase(heroesRepository);
    }

    @Test
    void should_return_all_heroes() {
        // Arrange
        Hero h1 = HeroMother.create(null, null, null, null, null, null, null, null, null);
        Hero h2 = HeroMother.create(null, null, null, null, null, null, null, null, null);
        List<Hero> heroes = Arrays.asList(h1, h2);

        when(heroesRepository.findAll()).thenReturn(heroes);

        // Act
        List<Hero> result = getHeroesUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(h1.id().value(), result.get(0).id().value());

        verify(heroesRepository).findAll();
    }

    @Test
    void should_return_empty_list_when_no_heroes() {
        // Arrange
        when(heroesRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Hero> result = getHeroesUseCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(heroesRepository).findAll();
    }
}
