package com.ruben.lotr.core.character.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

//import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import com.ruben.lotr.core.character.domain.model.Hero;
import com.ruben.lotr.core.character.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.character.domain.valueobject.BreedIdVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroIdVO;
import com.ruben.lotr.core.character.domain.valueobject.SideIdVO;

@Repository
// @Primary
public class HybernateHeroesRepository implements HeroesRepositoryInterface {

    @PersistenceContext
    private EntityManager entityManager;

    public HybernateHeroesRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Hero> findAll() {
        String jpql = "SELECT new Hero(" +
                "h.id, h.name, h.lastName, b.name, s.name, " +
                "h.eyesColor, h.hairColor, h.height, h.description) " +
                "FROM HeroEntity h " +
                "JOIN h.breed b " +
                "JOIN h.side s";

        TypedQuery<Hero> query = entityManager.createQuery(jpql, Hero.class);
        return query.getResultList();
    }

    @Override
    public Optional<Hero> findById(HeroIdVO id) {
        String jpql = "SELECT new Hero(" +
                "h.id, h.name, h.lastName, b.name, s.name, " +
                "h.eyesColor, h.hairColor, h.height, h.description) " +
                "FROM HeroEntity h " +
                "JOIN h.breed b " +
                "JOIN h.side s " +
                "WHERE h.id = :id";

        TypedQuery<Hero> query = entityManager.createQuery(jpql, Hero.class);
        query.setParameter("id", id.value());

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Hero> searchByBreedId(BreedIdVO breedId) {
        String jpql = "SELECT new Hero(" +
                "h.id, h.name, h.lastName, b.name, s.name, h.eyesColor, h.hairColor, h.height, h.description) " +
                "FROM HeroEntity h " +
                "JOIN h.breed b " +
                "JOIN h.side s " +
                "WHERE b.id = :breedId";
        TypedQuery<Hero> query = entityManager.createQuery(
                jpql, Hero.class);
        query.setParameter("breedId", breedId.value());
        return query.getResultList();
    }

    @Override
    public List<Hero> searchBySideId(SideIdVO sideId) {
        String jpql = "SELECT new Hero(" +
                "h.id, h.name, h.lastName, b.name, s.name, h.eyesColor, h.hairColor, h.height, h.description) " +
                "FROM HeroEntity h " +
                "JOIN h.breed b " +
                "JOIN h.side s " +
                "WHERE s.id = :sideId";
        TypedQuery<Hero> query = entityManager.createQuery(
                jpql, Hero.class);
        query.setParameter("sideId", sideId.value());

        return query.getResultList();
    }
}
