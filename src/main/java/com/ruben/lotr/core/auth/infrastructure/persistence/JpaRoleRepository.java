package com.ruben.lotr.core.auth.infrastructure.persistence;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.ruben.lotr.core.auth.domain.model.Role;
import com.ruben.lotr.core.auth.domain.model.RoleName;
import com.ruben.lotr.core.auth.domain.repository.RoleRepositoryInterface;

@Repository
@Profile("hibernate")
public class JpaRoleRepository implements RoleRepositoryInterface {

    private final SpringDataRoleRepository repository;

    public JpaRoleRepository(SpringDataRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Role> findByName(RoleName roleName) {
        return repository.findByName(roleName.name())
                .map(UserMapper::toRole);
    }
}