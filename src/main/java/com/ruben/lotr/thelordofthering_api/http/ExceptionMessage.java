package com.ruben.lotr.thelordofthering_api.http;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class ExceptionMessage {

    public static String getMessage(Throwable exception) {
        if (exception instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException ex = (MethodArgumentTypeMismatchException) exception;
            return String.format("Format not allowed: <%s> must be a number.",
                    ex.getValue() != null ? ex.getValue() : "null");
        }
        return exception.getMessage() != null ? exception.getMessage() : "Internal server error.";
    }
}