package com.ruben.lotr.core.auth.infrastructure.security;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class BCryptPasswordHasherTest {

    @Test
    void should_hash_and_match_password_when_input_is_valid() {
        // Arrange
        BCryptPasswordHasher hasher = new BCryptPasswordHasher();
        String password = "ringring";

        // Act
        String hash = hasher.hash(password);
        boolean matches = hasher.matches(password, hash);

        // Assert
        assertNotNull(hash);
        assertTrue(matches);
    }

    @Test
    void should_return_false_when_password_does_not_match() {
        // Arrange
        BCryptPasswordHasher hasher = new BCryptPasswordHasher();
        String correctPassword = "correct";
        String incorrectPassword = "incorrect";
        String hash = hasher.hash(correctPassword);

        // Act
        boolean matches = hasher.matches(incorrectPassword, hash);

        // Assert
        assertFalse(matches);
    }

    @Test
    void should_throw_exception_when_hash_password_is_null() {
        // Arrange
        BCryptPasswordHasher hasher = new BCryptPasswordHasher();

        // Act + Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> hasher.hash(null));

        assertEquals("Password cannot be null", exception.getMessage());
    }

    @Test
    void should_return_false_when_match_inputs_are_null() {
        // Arrange
        BCryptPasswordHasher hasher = new BCryptPasswordHasher();

        // Act
        boolean matches = hasher.matches(null, null);

        // Assert
        assertFalse(matches);
    }
}
