package com.ruben.lotr.core.auth.domain.repository;

import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserIdVO;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;

public interface UserRepositoryInterface {

    Optional<User> findByEmail(UserEmailVO email);

    Optional<User> findById(UserIdVO id);

    List<User> findAll();

    void deleteById(UserIdVO id);

    void save(@NonNull User user);
}
