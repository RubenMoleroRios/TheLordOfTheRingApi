package com.ruben.lotr.api.http;

import com.ruben.lotr.core.auth.domain.exception.InvalidCredentialsException;

import org.hibernate.TypeMismatchException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("unit")
class ExceptionHttpStatusMapperTest {

    static class DummyController {
        @SuppressWarnings("unused")
        public void getById(Integer id) {
        }
    }

    @Test
    void should_return_bad_request_when_exception_is_method_argument_type_mismatch() throws Exception {
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
        HttpStatusEnum status = ExceptionHttpStatusMapper.map(exception);

        // Assert
        assertEquals(HttpStatusEnum.BAD_REQUEST, status);
    }

    @Test
    void should_return_bad_request_when_exception_is_type_mismatch_exception() {
        // Arrange
        TypeMismatchException exception = mock(TypeMismatchException.class);

        // Act
        HttpStatusEnum status = ExceptionHttpStatusMapper.map(exception);

        // Assert
        assertEquals(HttpStatusEnum.BAD_REQUEST, status);
        verifyNoInteractions(exception);
    }

    @Test
    void should_return_bad_request_when_exception_cause_is_type_mismatch_exception() {
        // Arrange
        TypeMismatchException cause = mock(TypeMismatchException.class);
        RuntimeException exception = new RuntimeException(cause);

        // Act
        HttpStatusEnum status = ExceptionHttpStatusMapper.map(exception);

        // Assert
        assertEquals(HttpStatusEnum.BAD_REQUEST, status);
        verifyNoInteractions(cause);
    }

    @Test
    void should_return_not_found_when_exception_is_domain_exception() {
        // Arrange
        InvalidCredentialsException exception = new InvalidCredentialsException();

        // Act
        HttpStatusEnum status = ExceptionHttpStatusMapper.map(exception);

        // Assert
        assertEquals(HttpStatusEnum.NOT_FOUND, status);
    }

    @Test
    void should_return_internal_server_error_when_exception_is_unknown() {
        // Arrange
        RuntimeException exception = new RuntimeException("Boom");

        // Act
        HttpStatusEnum status = ExceptionHttpStatusMapper.map(exception);

        // Assert
        assertEquals(HttpStatusEnum.INTERNAL_SERVER_ERROR, status);
    }
}
