package com.ruben.lotr.thelordofthering_api.http;

import org.springframework.http.HttpStatus;

public enum HttpStatusEnum {
    OK(HttpStatus.OK),
    CREATED(HttpStatus.CREATED),
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
    FORBIDDEN(HttpStatus.FORBIDDEN),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    CONFLICT(HttpStatus.CONFLICT),
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

    private final HttpStatus status;

    HttpStatusEnum(HttpStatus status) {
        this.status = status;
    }

    public int value() {
        return status.value();
    }

    public String getReasonPhrase() {
        return status.getReasonPhrase();
    }

    public HttpStatus toHttpStatus() {
        return this.status;
    }
}
