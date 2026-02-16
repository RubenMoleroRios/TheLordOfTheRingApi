package com.ruben.lotr.core.auth.application.usecase;

import com.ruben.lotr.core.auth.application.dto.AuthResponse;
import com.ruben.lotr.core.auth.application.service.JwtTokenGenerator;
import com.ruben.lotr.core.auth.domain.exception.UserAlreadyExistsException;
import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.repository.UserRepositoryInterface;
import com.ruben.lotr.core.auth.domain.service.PasswordHasher;
import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;
import com.ruben.lotr.core.auth.domain.model.UserMother;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.util.Optional;

import static com.ruben.lotr.core.auth.application.usecase.RegisterUserCommandMother.with;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@Tag("unit")
class RegisterUseCaseTest {

        private UserRepositoryInterface userRepository;
        private PasswordHasher passwordHasher;
        private JwtTokenGenerator jwtTokenGenerator;

        private RegisterUseCase registerUseCase;

        @BeforeEach
        void setUp() {
                userRepository = mock(UserRepositoryInterface.class);
                passwordHasher = mock(PasswordHasher.class);
                jwtTokenGenerator = mock(JwtTokenGenerator.class);

                registerUseCase = new RegisterUseCase(
                                userRepository,
                                passwordHasher,
                                jwtTokenGenerator);
        }

        @Test
        void should_register_user_and_return_auth_response_when_data_is_valid() {
                // Arrange
                String name = "Frodo";
                String email = "frodo@gmail.com";
                String password = "ringring";

                RegisterUserCommand command = with(name, email, password);

                when(userRepository.findByEmail(any(UserEmailVO.class)))
                                .thenReturn(Optional.empty());

                when(passwordHasher.hash(password))
                                .thenReturn("hashed-password12345");

                when(jwtTokenGenerator.generate(any(User.class)))
                                .thenReturn("token-123");

                // Act
                AuthResponse response = registerUseCase.execute(command);

                // Assert (state)
                assertNotNull(response);
                assertEquals("token-123", response.token());
                assertEquals(email, response.user().email());
                assertEquals(name, response.user().name());

                // Assert (interaction)
                verify(userRepository)
                                .findByEmail(argThat(vo -> vo.value().equals(email)));

                verify(passwordHasher)
                                .hash(password);

                // verify
                ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
                verify(userRepository).save(userCaptor.capture());

                User savedUser = userCaptor.getValue();
                assertNotNull(savedUser);
                assertEquals(email, savedUser.email().value());
                assertEquals(name, savedUser.name().value());

                verify(jwtTokenGenerator)
                                .generate(savedUser);
        }

        @Test
        void should_throw_exception_when_user_already_exists() {
                // Arrange
                String email = "frodo@gmail.com";

                RegisterUserCommand command = with("Frodo", email, "ringring");

                User existingUser = UserMother.create(null, UserEmailVO.create(email), null);

                when(userRepository.findByEmail(any(UserEmailVO.class)))
                                .thenReturn(Optional.of(existingUser));

                // Act + Assert
                assertThrows(
                                UserAlreadyExistsException.class,
                                () -> registerUseCase.execute(command));

                // verify
                verifyNoInteractions(passwordHasher, jwtTokenGenerator);
                verify(userRepository, never()).save(any(User.class));
        }

}
