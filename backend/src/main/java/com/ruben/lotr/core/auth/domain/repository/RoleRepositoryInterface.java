package com.ruben.lotr.core.auth.domain.repository;

import java.util.Optional;

import com.ruben.lotr.core.auth.domain.model.Role;
import com.ruben.lotr.core.auth.domain.model.RoleName;

public interface RoleRepositoryInterface {

    Optional<Role> findByName(RoleName roleName);
}