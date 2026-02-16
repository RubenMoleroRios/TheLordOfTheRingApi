package com.ruben.lotr.core.shared.domain.valueobject;

import com.ruben.lotr.core.auth.domain.valueobject.UserNameVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroEyesColorVO;
import com.ruben.lotr.core.shared.domain.exception.InvalidStringLengthException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class StringValueObjectTest {

    @Test
    void should_trim_value_when_created() {
        // Arrange
        String raw = "  Frodo  ";

        // Act
        UserNameVO vo = UserNameVO.create(raw);

        // Assert
        assertEquals("Frodo", vo.value());
    }

    @Test
    void should_throw_exception_when_null_is_not_allowed() {
        // Arrange
        String expectedMessage = "The length must be between <3> and <50> characters, this is the actual length <0>.";

        // Act + Assert
        InvalidStringLengthException exception = assertThrows(
                InvalidStringLengthException.class,
                () -> UserNameVO.create(null));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void should_throw_exception_when_value_is_too_short() {
        // Arrange
        String expectedMessage = "The length must be between <3> and <50> characters, this is the actual length <2>.";

        // Act + Assert
        InvalidStringLengthException exception = assertThrows(
                InvalidStringLengthException.class,
                () -> UserNameVO.create("ab"));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void should_allow_null_when_value_object_allows_null() {
        // Arrange

        // Act
        HeroEyesColorVO vo = HeroEyesColorVO.create(null);

        // Assert
        assertNull(vo.value());
    }
}
