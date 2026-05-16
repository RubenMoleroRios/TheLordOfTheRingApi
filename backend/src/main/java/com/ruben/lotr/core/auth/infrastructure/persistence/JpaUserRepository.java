package com.ruben.lotr.core.auth.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.repository.UserRepositoryInterface;
import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserIdVO;

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
    public Optional<User> findById(UserIdVO id) {
        return repository.findById(UUID.fromString(id.value()))
                .map(UserMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UserIdVO id) {
        repository.deleteById(UUID.fromString(id.value()));
    }

    @Override
    public void save(@NonNull User user) {
        repository.save(UserMapper.toEntity(user));
    }
}
