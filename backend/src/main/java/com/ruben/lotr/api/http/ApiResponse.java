package com.ruben.lotr.api.http;

import org.springframework.http.ResponseEntity;

import com.ruben.lotr.core.shared.domain.exception.BaseDomainException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ApiResponse {

    public static ResponseEntity<Map<String, Object>> fromException(Throwable exception) {
        HttpStatusEnum status = ExceptionHttpStatusMapper.map(exception);
        Map<String, Object> response = new HashMap<>();

        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", "error");
        response.put("message", ExceptionMessage.getMessage(exception));
        response.put("data", "");
        response.put("error", status.getReasonPhrase());

        if (exception instanceof BaseDomainException) {
            response.put("code", exception.getClass().getSimpleName());
        }

        return new ResponseEntity<>(response, status.toHttpStatus());
    }

    public static ResponseEntity<Map<String, Object>> success(
            HttpStatusEnum status,
            Object data,
            String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", "success");
        response.put("message", message);
        response.put("data", data);
        response.put("error", "");
        return new ResponseEntity<>(response, status.toHttpStatus());
    }

}