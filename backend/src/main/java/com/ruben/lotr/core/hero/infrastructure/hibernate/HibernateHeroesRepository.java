package com.ruben.lotr.core.hero.infrastructure.hibernate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideIdVO;
import com.ruben.lotr.core.hero.infrastructure.hibernate.entities.BreedEntity;
import com.ruben.lotr.core.hero.infrastructure.hibernate.entities.HeroEntity;
import com.ruben.lotr.core.hero.infrastructure.hibernate.entities.SideEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

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
    @Transactional
    public Hero save(Hero hero) {
        BreedEntity breedReference = entityManager.getReference(BreedEntity.class,
                UUID.fromString(hero.breed().id().value()));
        SideEntity sideReference = entityManager.getReference(SideEntity.class,
                UUID.fromString(hero.side().id().value()));

        HeroEntity entity = mapper.toEntity(hero, breedReference, sideReference);
        HeroEntity saved = entityManager.merge(entity);
        entityManager.flush();
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional
    public void deleteById(HeroIdVO id) {
        HeroEntity entity = entityManager.find(HeroEntity.class, UUID.fromString(id.value()));
        if (entity != null) {
            entityManager.remove(entity);
            entityManager.flush();
        }
    }

    @Override
    public List<Hero> findAll() {
        String jpql = """
                    SELECT h
                    FROM HeroEntity h
                    JOIN FETCH h.breed
                    JOIN FETCH h.side
                    ORDER BY h.breed.name ASC, h.name ASC
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

        String jpql = """
                    SELECT h
                    FROM HeroEntity h
                    JOIN FETCH h.breed
                    JOIN FETCH h.side
                    WHERE h.id = :heroId
                """;

        TypedQuery<HeroEntity> query = entityManager.createQuery(jpql, HeroEntity.class);
        query.setParameter("heroId", heroId);

        HeroEntity entity = query.getResultStream().findFirst().orElse(null);

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
                    ORDER BY h.name ASC
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
                    ORDER BY h.name ASC
                """;

        TypedQuery<HeroEntity> query = entityManager.createQuery(jpql, HeroEntity.class);

        query.setParameter("sideId", UUID.fromString(sideId.value()));

        return query.getResultList()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
