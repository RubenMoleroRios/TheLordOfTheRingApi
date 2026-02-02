package com.ruben.lotr.core.auth.application.usecase;

import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.repository.UserRepositoryInterface;
import com.ruben.lotr.core.auth.domain.service.PasswordHasher;
import com.ruben.lotr.core.auth.domain.valueobject.*;
import com.ruben.lotr.core.auth.application.dto.AuthResponse;
import com.ruben.lotr.core.auth.application.mapper.AuthResponseMapper;
import com.ruben.lotr.core.auth.application.service.JwtTokenGenerator;
import com.ruben.lotr.core.auth.application.valueobject.PlainPassword;
import com.ruben.lotr.core.auth.domain.exception.UserAlreadyExistsException;

import org.springframework.stereotype.Service;

@Service
public final class RegisterUseCase {

    private final UserRepositoryInterface userRepository;
    private final PasswordHasher passwordHasher;
    private final JwtTokenGenerator jwtTokenGenerator;

    public RegisterUseCase(
            UserRepositoryInterface userRepository,
            PasswordHasher passwordHasher,
            JwtTokenGenerator jwtTokenGenerator) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    public AuthResponse execute(RegisterUserCommand command) {

        UserEmailVO email = UserEmailVO.create(command.email());

        userRepository.findByEmail(email).ifPresent(user -> {
            throw new UserAlreadyExistsException(email.value());
        });

        PlainPassword plainPassword = new PlainPassword(command.password());
        String hash = passwordHasher.hash(plainPassword.value());

        User user = User.create(
                UserNameVO.create(command.name()),
                UserEmailVO.create(command.email()),
                UserPasswordHashVO.fromHash(hash));

        userRepository.save(user);

        String token = jwtTokenGenerator.generate(user);
        return AuthResponseMapper.from(user, token);
    }
}
