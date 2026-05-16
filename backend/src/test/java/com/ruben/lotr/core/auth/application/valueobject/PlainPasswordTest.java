package com.ruben.lotr.core.auth.application.valueobject;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class PlainPasswordTest {

    @Test
    void should_create_plain_password_when_value_is_valid() {
        // Arrange
        String password = "validPass123";

        // Act
        PlainPassword plainPassword = new PlainPassword(password);

        // Assert
        assertNotNull(plainPassword);
        assertEquals(password, plainPassword.value());
    }

    @Test
    void should_throw_exception_when_password_is_too_short() {
        // Arrange
        String password = "short";

        // Act + Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PlainPassword(password));

        assertEquals("Password too short", exception.getMessage());
    }

    @Test
    void should_throw_exception_when_password_is_null() {
        // Act + Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PlainPassword(null));

        assertEquals("Password too short", exception.getMessage());
    }
}
