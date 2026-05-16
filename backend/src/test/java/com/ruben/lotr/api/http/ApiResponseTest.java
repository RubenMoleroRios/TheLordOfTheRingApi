package com.ruben.lotr.api.http;

import com.ruben.lotr.core.auth.domain.exception.InvalidCredentialsException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class ApiResponseTest {

    @Test
    void should_return_success_response_when_success_is_called() {
        // Arrange
        Object data = Map.of("key", "value");
        String message = "ok";

        // Act
        ResponseEntity<Map<String, Object>> response = ApiResponse.success(
                HttpStatusEnum.OK,
                data,
                message);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("success", body.get("status"));
        assertEquals(message, body.get("message"));
        assertEquals(data, body.get("data"));
        assertEquals("", body.get("error"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    void should_include_code_and_not_found_status_when_exception_is_domain_exception() {
        // Arrange
        Throwable exception = new InvalidCredentialsException();

        // Act
        ResponseEntity<Map<String, Object>> response = ApiResponse.fromException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatusEnum.NOT_FOUND.toHttpStatus(), response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("error", body.get("status"));
        assertEquals("Invalid credentials", body.get("message"));
        assertEquals("", body.get("data"));
        assertEquals(HttpStatusEnum.NOT_FOUND.getReasonPhrase(), body.get("error"));
        assertEquals("InvalidCredentialsException", body.get("code"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    void should_not_include_code_and_return_500_when_exception_is_not_domain_exception() {
        // Arrange
        Throwable exception = new RuntimeException("Boom");

        // Act
        ResponseEntity<Map<String, Object>> response = ApiResponse.fromException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatusEnum.INTERNAL_SERVER_ERROR.toHttpStatus(), response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("error", body.get("status"));
        assertEquals("Boom", body.get("message"));
        assertEquals("", body.get("data"));
        assertEquals(HttpStatusEnum.INTERNAL_SERVER_ERROR.getReasonPhrase(), body.get("error"));
        assertFalse(body.containsKey("code"));
        assertNotNull(body.get("timestamp"));
    }
}
