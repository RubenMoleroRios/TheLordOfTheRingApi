package com.ruben.lotr.core.auth.infrastructure.persistence;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruben.lotr.core.auth.infrastructure.persistence.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("hibernate")
public interface SpringDataUserRepository
        extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);
}
