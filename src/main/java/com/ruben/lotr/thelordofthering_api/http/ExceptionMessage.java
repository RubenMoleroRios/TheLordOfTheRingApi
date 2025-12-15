package com.ruben.lotr.thelordofthering_api.http;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class ExceptionMessage {

    public static String getMessage(Throwable exception) {
        if (exception instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException ex = (MethodArgumentTypeMismatchException) exception;
            return String.format("Formato no permitido: <%s> debe estar compuesto solo por números.",
                    ex.getValue() != null ? ex.getValue() : "null");
        }
        return exception.getMessage() != null ? exception.getMessage() : "Error interno del servidor";
    }
}