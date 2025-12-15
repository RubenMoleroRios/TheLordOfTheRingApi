package com.ruben.lotr.thelordofthering_api.repositories.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import com.ruben.lotr.thelordofthering_api.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.repositories.interfaces.HeroesRepositoryInterface;

@Repository
@Primary
public class HybernateHeroesRepository implements HeroesRepositoryInterface {

    @PersistenceContext
    private EntityManager entityManager;

    public HybernateHeroesRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<HeroDTO> findAllHeroes() {
        String jpql = "SELECT new HeroDTO(" +
                "h.id, h.name, h.lastName, b.name, s.name, " +
                "h.eyesColor, h.hairColor, h.height, h.description) " +
                "FROM HeroEntity h " +
                "JOIN h.breed b " +
                "JOIN h.side s";

        TypedQuery<HeroDTO> query = entityManager.createQuery(jpql, HeroDTO.class);
        return query.getResultList();
    }

    @Override
    public Optional<HeroDTO> findById(Long id) {
        String jpql = "SELECT new HeroDTO(" +
                "h.id, h.name, h.lastName, b.name, s.name, " +
                "h.eyesColor, h.hairColor, h.height, h.description) " +
                "FROM HeroEntity h " +
                "JOIN h.breed b " +
                "JOIN h.side s " +
                "WHERE h.id = :id";

        TypedQuery<HeroDTO> query = entityManager.createQuery(jpql, HeroDTO.class);
        query.setParameter("id", id);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<HeroDTO> searchByBreedId(Long breed) {
        String jpql = "SELECT new HeroDTO(" +
                "h.id, h.name, h.lastName, b.name, s.name, h.eyesColor, h.hairColor, h.height, h.description) " +
                "FROM HeroEntity h " +
                "JOIN h.breed b " +
                "JOIN h.side s " +
                "WHERE b.id = :breed";
        TypedQuery<HeroDTO> query = entityManager.createQuery(
                jpql, HeroDTO.class);
        query.setParameter("breed", breed);
        return query.getResultList();
    }

    @Override
    public List<HeroDTO> searchBySideId(Long side) {
        String jpql = "SELECT new HeroDTO(" +
                "h.id, h.name, h.lastName, b.name, s.name, h.eyesColor, h.hairColor, h.height, h.description) " +
                "FROM HeroEntity h " +
                "JOIN h.breed b " +
                "JOIN h.side s " +
                "WHERE s.id = :side";
        TypedQuery<HeroDTO> query = entityManager.createQuery(
                jpql, HeroDTO.class);
        query.setParameter("side", side);

        return query.getResultList();
    }
}
