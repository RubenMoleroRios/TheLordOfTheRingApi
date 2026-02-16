package com.ruben.lotr.core.shared.domain.valueobject;

import com.ruben.lotr.core.auth.domain.valueobject.UserIdVO;
import com.ruben.lotr.core.shared.domain.exception.InvalidUuidException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class UuidValueObjectTest {

    @Test
    void should_create_uuid_value_object_when_value_is_valid_uuid() {
        // Arrange
        String uuid = "11111111-1111-1111-1111-111111111111";

        // Act
        UserIdVO id = UserIdVO.create(uuid);

        // Assert
        assertEquals(uuid, id.value());
    }

    @Test
    void should_throw_exception_when_value_is_not_a_valid_uuid() {
        // Arrange
        String invalid = "not-a-uuid";
        String expectedMessage = "The value <not-a-uuid> is not a valid UUID for class <UserIdVO>";

        // Act + Assert
        InvalidUuidException exception = assertThrows(
                InvalidUuidException.class,
                () -> UserIdVO.create(invalid));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void should_be_equal_when_values_are_equal() {
        // Arrange
        String uuid = "22222222-2222-2222-2222-222222222222";

        // Act
        UserIdVO firstId = UserIdVO.create(uuid);
        UserIdVO secondId = UserIdVO.create(uuid);

        // Assert
        assertEquals(firstId, secondId);
        assertEquals(firstId.hashCode(), secondId.hashCode());
    }
}
