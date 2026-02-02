package com.ruben.lotr.core.auth.application.usecase;

import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.repository.UserRepositoryInterface;
import com.ruben.lotr.core.auth.domain.service.PasswordHasher;
import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.auth.application.dto.AuthResponse;
import com.ruben.lotr.core.auth.application.mapper.AuthResponseMapper;
import com.ruben.lotr.core.auth.application.service.JwtTokenGenerator;
import com.ruben.lotr.core.auth.domain.exception.InvalidCredentialsException;

@Service
public final class LoginUseCase {

    private final UserRepositoryInterface userRepository;
    private final PasswordHasher passwordHasher;
    private final JwtTokenGenerator jwtTokenGenerator;

    public LoginUseCase(
            UserRepositoryInterface userRepository,
            PasswordHasher passwordHasher,
            JwtTokenGenerator jwtTokenGenerator) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    public AuthResponse execute(LoginUserCommand command) {

        UserEmailVO email = UserEmailVO.create(command.email());

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        boolean valid = passwordHasher.matches(
                command.password(),
                user.password().value());

        if (!valid) {
            throw new InvalidCredentialsException();
        }
        String token = jwtTokenGenerator.generate(user);

        return AuthResponseMapper.from(user, token);
    }
}
