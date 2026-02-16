package com.ruben.lotr.core.auth.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import com.ruben.lotr.core.shared.domain.exception.InvalidStringLengthException;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class UserPasswordHashVOTest {

    @Test
    void should_create_password_hash_when_hash_is_valid() {
        // Arrange
        String hash = "this-is-a-valid-hash-123456";

        // Act
        UserPasswordHashVO passwordHash = UserPasswordHashVO.fromHash(hash);

        // Assert
        assertNotNull(passwordHash);
        assertEquals(hash, passwordHash.value());
    }

    @Test
    void should_throw_exception_when_hash_is_too_short() {
        // Arrange
        String shortHash = "short-hash";

        // Act + Assert
        assertThrows(
                InvalidStringLengthException.class,
                () -> UserPasswordHashVO.fromHash(shortHash));
    }

    @Test
    void should_throw_exception_when_hash_is_null() {
        // Act + Assert
        assertThrows(
                InvalidStringLengthException.class,
                () -> UserPasswordHashVO.fromHash(null));
    }
}
