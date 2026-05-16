package com.ruben.lotr.core.auth.infrastructure.persistence;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.auth.domain.model.Permission;
import com.ruben.lotr.core.auth.domain.model.Role;
import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.valueobject.UserNameVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserIdVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserPasswordHashVO;
import com.ruben.lotr.core.auth.infrastructure.persistence.entity.PermissionEntity;
import com.ruben.lotr.core.auth.infrastructure.persistence.entity.RoleEntity;
import com.ruben.lotr.core.auth.infrastructure.persistence.entity.UserEntity;

public final class UserMapper {

    public static @NonNull UserEntity toEntity(@NonNull User user) {
        return new UserEntity(
                UUID.fromString(user.id().value()),
                user.name().value(),
                user.email().value(),
                user.password().value(),
                toRoleEntity(user.role()));
    }

    public static @NonNull User toDomain(@NonNull UserEntity entity) {
        return User.rehydrate(
                UserIdVO.create(entity.getId().toString()),
                UserNameVO.create(entity.getName()),
                UserEmailVO.create(entity.getEmail()),
                UserPasswordHashVO.fromHash(entity.getPassword()),
                toRole(entity.getRole()));
    }

    private static RoleEntity toRoleEntity(Role role) {
        return new RoleEntity(
                UUID.fromString(role.id()),
                role.name(),
                role.permissions().stream()
                        .map(UserMapper::toPermissionEntity)
                        .collect(Collectors.toCollection(java.util.LinkedHashSet::new)));
    }

    private static PermissionEntity toPermissionEntity(Permission permission) {
        return new PermissionEntity(UUID.fromString(permission.id()), permission.name());
    }

    public static Role toRole(RoleEntity entity) {
        return Role.create(
                entity.getId().toString(),
                entity.getName(),
                entity.getPermissions().stream().map(UserMapper::toPermission).toList());
    }

    private static Permission toPermission(PermissionEntity entity) {
        return Permission.create(entity.getId().toString(), entity.getName());
    }
}
