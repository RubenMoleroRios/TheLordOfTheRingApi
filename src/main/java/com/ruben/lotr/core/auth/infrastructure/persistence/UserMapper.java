package com.ruben.lotr.core.auth.infrastructure.persistence;

import java.util.UUID;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.valueobject.UserNameVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserIdVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserPasswordVO;
import com.ruben.lotr.core.auth.infrastructure.persistence.entity.UserEntity;

public final class UserMapper {

    public static @NonNull UserEntity toEntity(@NonNull User user) {
        return new UserEntity(
                UUID.fromString(user.id().value()),
                user.name().value(),
                user.email().value(),
                user.password().value());
    }

    public static @NonNull User toDomain(@NonNull UserEntity entity) {
        return User.fromPersistence(
                UserIdVO.create(entity.getId().toString()),
                UserNameVO.create(entity.getName()),
                UserEmailVO.create(entity.getEmail()),
                UserPasswordVO.fromHashed(entity.getPassword()));
    }
}
