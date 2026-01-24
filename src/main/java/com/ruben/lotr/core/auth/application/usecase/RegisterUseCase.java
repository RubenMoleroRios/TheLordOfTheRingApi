package com.ruben.lotr.core.auth.application.usecase;

import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.repository.UserRepositoryInterface;
import com.ruben.lotr.core.auth.domain.service.PasswordHasher;
import com.ruben.lotr.core.auth.domain.valueobject.*;
import com.ruben.lotr.core.auth.domain.exception.UserAlreadyExistsException;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public final class RegisterUseCase {

    private final UserRepositoryInterface userRepository;
    private final PasswordHasher passwordHasher;

    public RegisterUseCase(
            UserRepositoryInterface userRepository,
            PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public void execute(@NonNull RegisterUserCommand command) {

        String hashedPassword = passwordHasher.hash(command.password());

        UserEmailVO email = UserEmailVO.create(command.email());

        userRepository.findByEmail(email).ifPresent(user -> {
            throw new UserAlreadyExistsException(email.value());
        });

        User user = User.create(
                UserNameVO.create(command.name()),
                UserEmailVO.create(command.email()),
                UserPasswordVO.fromHashed(hashedPassword));

        userRepository.save(user);
    }
}
