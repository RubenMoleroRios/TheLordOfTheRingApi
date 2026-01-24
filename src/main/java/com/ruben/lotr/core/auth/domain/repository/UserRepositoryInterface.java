package com.ruben.lotr.core.auth.domain.repository;

import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;

import java.util.Optional;

import org.springframework.lang.NonNull;

public interface UserRepositoryInterface {

    Optional<User> findByEmail(UserEmailVO email);

    void save(@NonNull User user);
}
