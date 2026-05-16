package com.ruben.lotr.api.http;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class ExceptionMessageTest {

    static class DummyController {
        @SuppressWarnings("unused")
        public void getById(Integer id) {
        }
    }

    @Test
    void should_return_custom_message_when_exception_is_method_argument_type_mismatch() throws Exception {
        // Arrange
        Method method = DummyController.class.getMethod("getById", Integer.class);
        MethodParameter parameter = new MethodParameter(method, 0);

        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
                "abc",
                Integer.class,
                "id",
                parameter,
                null);

        // Act
        String message = ExceptionMessage.getMessage(exception);

        // Assert
        assertEquals("Format not allowed: <abc> must be a number.", message);
    }

    @Test
    void should_return_exception_message_when_exception_has_message() {
        // Arrange
        RuntimeException exception = new RuntimeException("Boom");

        // Act
        String message = ExceptionMessage.getMessage(exception);

        // Assert
        assertEquals("Boom", message);
    }

    @Test
    void should_return_default_message_when_exception_message_is_null() {
        // Arrange
        RuntimeException exception = new RuntimeException((String) null);

        // Act
        String message = ExceptionMessage.getMessage(exception);

        // Assert
        assertEquals("Internal server error.", message);
    }
}
