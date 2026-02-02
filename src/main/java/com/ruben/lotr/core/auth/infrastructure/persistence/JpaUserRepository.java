package com.ruben.lotr.core.auth.infrastructure.persistence;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.repository.UserRepositoryInterface;
import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;

@Repository
@Profile("hibernate")
public class JpaUserRepository implements UserRepositoryInterface {

    private final SpringDataUserRepository repository;

    public JpaUserRepository(SpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findByEmail(UserEmailVO email) {
        return repository.findByEmail(email.value())
                .map(UserMapper::toDomain);
    }

    @Override
    public void save(@NonNull User user) {
        repository.save(UserMapper.toEntity(user));
    }
}
