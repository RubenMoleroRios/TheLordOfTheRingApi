package com.ruben.lotr.core.hero.infrastructure.hibernate;

import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideIdVO;
import com.ruben.lotr.core.hero.infrastructure.hibernate.entities.HeroEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Tag("unit")
class HibernateHeroesRepositoryTest {

    private EntityManager entityManager;
    private HeroEntityMapper mapper;
    private HibernateHeroesRepository repository;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        mapper = mock(HeroEntityMapper.class);
        repository = new HibernateHeroesRepository(entityManager, mapper);
    }

    @Test
    void should_return_all_heroes_as_domain_when_find_all_is_called() {
        // Arrange
        TypedQuery<HeroEntity> query = mock(TypedQuery.class);

        HeroEntity entity1 = mock(HeroEntity.class);
        HeroEntity entity2 = mock(HeroEntity.class);

        Hero hero1 = mock(Hero.class);
        Hero hero2 = mock(Hero.class);

        when(entityManager.createQuery(anyString(), eq(HeroEntity.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(entity1, entity2));
        when(mapper.toDomain(entity1)).thenReturn(hero1);
        when(mapper.toDomain(entity2)).thenReturn(hero2);

        // Act
        List<Hero> result = repository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertSame(hero1, result.get(0));
        assertSame(hero2, result.get(1));

        verify(entityManager).createQuery(anyString(), eq(HeroEntity.class));
        verify(query).getResultList();
        verify(mapper).toDomain(entity1);
        verify(mapper).toDomain(entity2);
    }

    @Test
    void should_return_optional_empty_when_entity_manager_returns_null_on_find_by_id() {
        // Arrange
        HeroIdVO heroId = HeroIdVO.generate();
        when(entityManager.find(eq(HeroEntity.class), any(UUID.class))).thenReturn(null);

        // Act
        Optional<Hero> result = repository.findById(heroId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(entityManager).find(eq(HeroEntity.class), eq(UUID.fromString(heroId.value())));
        verifyNoInteractions(mapper);
    }

    @Test
    void should_return_optional_of_domain_when_find_by_id_finds_entity() {
        // Arrange
        HeroIdVO heroId = HeroIdVO.generate();
        HeroEntity entity = mock(HeroEntity.class);
        Hero hero = mock(Hero.class);

        when(entityManager.find(eq(HeroEntity.class), any(UUID.class))).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(hero);

        // Act
        Optional<Hero> result = repository.findById(heroId);

        // Assert
        assertTrue(result.isPresent());
        assertSame(hero, result.get());

        verify(entityManager).find(eq(HeroEntity.class), eq(UUID.fromString(heroId.value())));
        verify(mapper).toDomain(entity);
    }

    @Test
    void should_search_by_breed_id_and_map_to_domain() {
        // Arrange
        TypedQuery<HeroEntity> query = mock(TypedQuery.class);
        HeroEntity entity = mock(HeroEntity.class);
        Hero hero = mock(Hero.class);

        BreedIdVO breedId = BreedIdVO.generate();

        when(entityManager.createQuery(anyString(), eq(HeroEntity.class))).thenReturn(query);
        when(query.setParameter(eq("breedId"), any(UUID.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(hero);

        // Act
        List<Hero> result = repository.searchByBreedId(breedId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(hero, result.get(0));

        verify(entityManager).createQuery(anyString(), eq(HeroEntity.class));
        verify(query).setParameter(eq("breedId"), eq(UUID.fromString(breedId.value())));
        verify(query).getResultList();
        verify(mapper).toDomain(entity);
    }

    @Test
    void should_search_by_side_id_and_map_to_domain() {
        // Arrange
        TypedQuery<HeroEntity> query = mock(TypedQuery.class);
        HeroEntity entity = mock(HeroEntity.class);
        Hero hero = mock(Hero.class);

        SideIdVO sideId = SideIdVO.generate();

        when(entityManager.createQuery(anyString(), eq(HeroEntity.class))).thenReturn(query);
        when(query.setParameter(eq("sideId"), any(UUID.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(hero);

        // Act
        List<Hero> result = repository.searchBySideId(sideId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(hero, result.get(0));

        verify(entityManager).createQuery(anyString(), eq(HeroEntity.class));
        verify(query).setParameter(eq("sideId"), eq(UUID.fromString(sideId.value())));
        verify(query).getResultList();
        verify(mapper).toDomain(entity);
    }
}
