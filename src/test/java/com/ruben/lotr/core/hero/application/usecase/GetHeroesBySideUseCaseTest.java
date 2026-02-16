package com.ruben.lotr.core.hero.application.usecase;

import com.ruben.lotr.core.hero.domain.exception.SideNotFoundException;
import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.model.Side;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideNameVO;
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
public class GetHeroesBySideUseCaseTest {

    private HeroesRepositoryInterface heroesRepository;
    private GetHeroesBySideUseCase getHeroesBySideUseCase;

    @BeforeEach
    void setUp() {
        heroesRepository = mock(HeroesRepositoryInterface.class);
        getHeroesBySideUseCase = new GetHeroesBySideUseCase(heroesRepository);
    }

    @Test
    void should_return_heroes_when_side_exists() {
        // Arrange
        SideIdVO sideId = SideIdVO.generate();
        Side side = Side.create(SideIdVO.create(sideId.value()), SideNameVO.create("Good"));
        Hero h1 = HeroMother.create(null, null, side, null, null, null, null, null, null);
        List<Hero> heroes = Arrays.asList(h1);

        when(heroesRepository.searchBySideId(any(SideIdVO.class))).thenReturn(heroes);

        // Act
        List<Hero> result = getHeroesBySideUseCase.execute(sideId.value());

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(heroesRepository).searchBySideId(argThat(vo -> vo.value().equals(sideId.value())));
    }

    @Test
    void should_throw_exception_when_side_not_found() {
        // Arrange
        SideIdVO sideId = SideIdVO.generate();

        when(heroesRepository.searchBySideId(any(SideIdVO.class))).thenReturn(Arrays.asList());

        // Act + Assert
        assertThrows(SideNotFoundException.class, () -> getHeroesBySideUseCase.execute(sideId.value()));

        verify(heroesRepository).searchBySideId(argThat(vo -> vo.value().equals(sideId.value())));
    }
}
