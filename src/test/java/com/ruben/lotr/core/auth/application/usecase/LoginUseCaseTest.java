package com.ruben.lotr.core.auth.application.usecase;

import com.ruben.lotr.core.auth.application.dto.AuthResponse;
import com.ruben.lotr.core.auth.application.service.JwtTokenGenerator;
import com.ruben.lotr.core.auth.domain.exception.InvalidCredentialsException;
import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.repository.UserRepositoryInterface;
import com.ruben.lotr.core.auth.domain.service.PasswordHasher;
import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;
import com.ruben.lotr.core.auth.domain.model.UserMother;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.ruben.lotr.core.auth.application.usecase.LoginUserCommandMother.with;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@Tag("unit")
class LoginUseCaseTest {

        private UserRepositoryInterface userRepository;
        private PasswordHasher passwordHasher;
        private JwtTokenGenerator jwtTokenGenerator;

        private LoginUseCase loginUseCase;

        @BeforeEach
        void setUp() {
                userRepository = mock(UserRepositoryInterface.class);
                passwordHasher = mock(PasswordHasher.class);
                jwtTokenGenerator = mock(JwtTokenGenerator.class);

                loginUseCase = new LoginUseCase(
                                userRepository,
                                passwordHasher,
                                jwtTokenGenerator);
        }

        @Test
        void should_return_auth_response_when_credentials_are_valid() {
                // Arrange
                String email = "frodo@gmail.com";
                String password = "ringring";

                User user = UserMother.create(null, UserEmailVO.create(email), null);
                LoginUserCommand command = with(email, password);

                when(userRepository.findByEmail(any(UserEmailVO.class)))
                                .thenReturn(Optional.of(user));

                when(passwordHasher.matches(password, user.password().value()))
                                .thenReturn(true);

                when(jwtTokenGenerator.generate(user))
                                .thenReturn("token-123");

                // Act
                AuthResponse response = loginUseCase.execute(command);

                // Assert (state)
                assertNotNull(response);
                assertEquals("token-123", response.token());
                assertEquals(email, response.user().email());
                assertEquals("Frodo", response.user().name());

                // Assert (interaction)
                verify(userRepository)
                                .findByEmail(argThat(vo -> vo.value().equals(email)));

                verify(passwordHasher)
                                .matches(password, user.password().value());

                verify(jwtTokenGenerator).generate(user);
        }

        @Test
        void should_throw_exception_when_user_not_found() {
                // Arrange
                LoginUserCommand command = with("frodo@gmail.com", "ringring");

                when(userRepository.findByEmail(any()))
                                .thenReturn(Optional.empty());

                // Act + Assert
                assertThrows(
                                InvalidCredentialsException.class,
                                () -> loginUseCase.execute(command));

                verify(passwordHasher, never()).matches(any(), any());
                verify(jwtTokenGenerator, never()).generate(any());
        }

        @Test
        void should_throw_exception_when_password_is_invalid() {
                // Arrange
                String email = "frodo@gmail.com";
                LoginUserCommand command = with(email, "wrong-pass");
                User user = UserMother.create(null, UserEmailVO.create(email), null);

                when(userRepository.findByEmail(any()))
                                .thenReturn(Optional.of(user));

                when(passwordHasher.matches(any(), any()))
                                .thenReturn(false);

                // Act + Assert
                assertThrows(
                                InvalidCredentialsException.class,
                                () -> loginUseCase.execute(command));

                verify(jwtTokenGenerator, never()).generate(any());
        }
}
