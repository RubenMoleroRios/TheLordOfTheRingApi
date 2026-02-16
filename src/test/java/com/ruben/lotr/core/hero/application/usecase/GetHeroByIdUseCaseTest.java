package com.ruben.lotr.core.hero.application.usecase;

import com.ruben.lotr.core.hero.domain.exception.HeroNotFoundException;
import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroIdVO;
import com.ruben.lotr.core.shared.domain.exception.InvalidUuidException;
import com.ruben.lotr.core.hero.domain.model.HeroMother;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@Tag("unit")
public class GetHeroByIdUseCaseTest {

        private HeroesRepositoryInterface heroesRepository;
        private GetHeroByIdUseCase getHeroeByIdUseCase;

        @BeforeEach
        void setUp() {
                heroesRepository = mock(HeroesRepositoryInterface.class);
                getHeroeByIdUseCase = new GetHeroByIdUseCase(heroesRepository);
        }

        @Test
        void should_return_hero_when_id_exists() {
                // Arrange
                HeroIdVO heroId = HeroIdVO.generate();
                Hero hero = HeroMother.create(heroId, null, null, null, null, null, null, null, null);

                when(heroesRepository.findById(any(HeroIdVO.class)))
                                .thenReturn(Optional.of(hero));

                // Act
                Hero result = getHeroeByIdUseCase.execute(heroId.value());

                // Assert (state)
                assertNotNull(result);
                assertEquals(heroId.value(), result.id().value());
                assertEquals(hero.name().value(), result.name().value());

                // Assert (interaction)
                verify(heroesRepository)
                                .findById(argThat(vo -> vo.value().equals(heroId.value())));
        }

        @Test
        void should_throw_exception_when_hero_not_found() {
                // Arrange
                HeroIdVO heroId = HeroIdVO.generate();

                when(heroesRepository.findById(any(HeroIdVO.class)))
                                .thenReturn(Optional.empty());

                // Act + Assert
                assertThrows(
                                HeroNotFoundException.class,
                                () -> getHeroeByIdUseCase.execute(heroId.value()));

                verify(heroesRepository)
                                .findById(argThat(vo -> vo.value().equals(heroId.value())));
        }

        @Test
        void should_throw_exception_when_id_is_not_a_valid_uuid() {
                // Arrange
                String invalidId = "not-a-uuid";

                // Act + Assert
                assertThrows(
                                InvalidUuidException.class,
                                () -> getHeroeByIdUseCase.execute(invalidId));

                verifyNoInteractions(heroesRepository);
        }
}
