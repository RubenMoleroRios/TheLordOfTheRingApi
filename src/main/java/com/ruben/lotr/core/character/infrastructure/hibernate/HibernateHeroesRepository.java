package com.ruben.lotr.core.character.infrastructure.hibernate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import com.ruben.lotr.core.character.domain.model.Hero;
import com.ruben.lotr.core.character.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.character.domain.valueobject.BreedIdVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroIdVO;
import com.ruben.lotr.core.character.domain.valueobject.SideIdVO;
import com.ruben.lotr.core.character.infrastructure.hibernate.entities.HeroEntity;

@Repository
@Profile("hibernate")
public class HibernateHeroesRepository implements HeroesRepositoryInterface {

    @PersistenceContext
    private EntityManager entityManager;

    private final HeroEntityMapper mapper;

    public HibernateHeroesRepository(EntityManager entityManager, HeroEntityMapper mapper) {
        this.entityManager = entityManager;
        this.mapper = mapper;
    }

    @Override
    public List<Hero> findAll() {
        String jpql = """
                    SELECT h
                    FROM HeroEntity h
                    JOIN FETCH h.breed
                    JOIN FETCH h.side
                """;

        TypedQuery<HeroEntity> query = entityManager.createQuery(jpql, HeroEntity.class);

        return query.getResultList()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Hero> findById(HeroIdVO id) {
        UUID heroId = UUID.fromString(id.value());

        HeroEntity entity = entityManager.find(HeroEntity.class, heroId);

        return Optional.ofNullable(entity)
                .map(mapper::toDomain);
    }

    @Override
    public List<Hero> searchByBreedId(BreedIdVO breedId) {
        String jpql = """
                    SELECT h
                    FROM HeroEntity h
                    JOIN FETCH h.breed
                    JOIN FETCH h.side
                    WHERE h.breed.id = :breedId
                """;

        TypedQuery<HeroEntity> query = entityManager.createQuery(jpql, HeroEntity.class);

        query.setParameter("breedId", UUID.fromString(breedId.value()));

        return query.getResultList()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Hero> searchBySideId(SideIdVO sideId) {
        String jpql = """
                    SELECT h
                    FROM HeroEntity h
                    JOIN FETCH h.breed
                    JOIN FETCH h.side
                    WHERE h.side.id = :sideId
                """;

        TypedQuery<HeroEntity> query = entityManager.createQuery(jpql, HeroEntity.class);

        query.setParameter("sideId", UUID.fromString(sideId.value()));

        return query.getResultList()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
