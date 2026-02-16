package com.ruben.lotr.core.auth.infrastructure.security;

import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.model.UserMother;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class JwtTokenGeneratorImplTest {

    private static final String SECRET = "01234567890123456789012345678901";
    private static final long EXPIRATION_MILLIS = 3_600_000L;

    @Test
    void should_generate_valid_token_and_extract_user_id() {
        // Arrange
        JwtTokenGeneratorImpl generator = new JwtTokenGeneratorImpl(SECRET, EXPIRATION_MILLIS);
        User user = UserMother.create(null, null, null);

        // Act
        String token = generator.generate(user);
        boolean valid = generator.validate(token);
        String userId = generator.getUserId(token);

        // Assert
        assertNotNull(token);
        assertTrue(valid);
        assertEquals(user.id().value(), userId);
    }

    @Test
    void should_return_false_when_token_is_invalid() {
        // Arrange
        JwtTokenGeneratorImpl generator = new JwtTokenGeneratorImpl(SECRET, EXPIRATION_MILLIS);
        String invalidToken = "invalid.token.value";

        // Act
        boolean valid = generator.validate(invalidToken);

        // Assert
        assertFalse(valid);
    }
}
