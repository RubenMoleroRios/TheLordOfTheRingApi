package com.ruben.lotr.core.hero.infrastructure.hibernate;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.ruben.lotr.api.dto.response.CatalogItemResponseDTO;
import com.ruben.lotr.core.hero.infrastructure.hibernate.entities.BreedEntity;
import com.ruben.lotr.core.hero.infrastructure.hibernate.entities.SideEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Profile("hibernate")
public class CatalogQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CatalogItemResponseDTO> findAllBreeds() {
        return entityManager.createQuery(
                "SELECT b FROM BreedEntity b ORDER BY b.name ASC",
                BreedEntity.class)
                .getResultList()
                .stream()
                .map(breed -> CatalogItemResponseDTO.from(breed.getId(), breed.getName()))
                .toList();
    }

    public List<CatalogItemResponseDTO> findAllSides() {
        return entityManager.createQuery(
                "SELECT s FROM SideEntity s ORDER BY s.name ASC",
                SideEntity.class)
                .getResultList()
                .stream()
                .map(side -> CatalogItemResponseDTO.from(side.getId(), side.getName()))
                .toList();
    }
}