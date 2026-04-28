package com.ruben.lotr.core.auth.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruben.lotr.core.auth.infrastructure.persistence.entity.RoleEntity;

@Repository
@Profile("hibernate")
public interface SpringDataRoleRepository extends JpaRepository<RoleEntity, UUID> {

    Optional<RoleEntity> findByName(String name);
}