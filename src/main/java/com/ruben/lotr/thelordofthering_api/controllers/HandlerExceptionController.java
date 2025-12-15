package com.ruben.lotr.thelordofthering_api.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ruben.lotr.thelordofthering_api.http.ApiResponse;

@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler(Throwable.class)
    public Object handleAllExceptions(Throwable exception) {
        return ApiResponse.fromException(exception);
    }
}
