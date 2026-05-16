package com.ruben.lotr.api.http;

import org.hibernate.TypeMismatchException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.ruben.lotr.core.shared.domain.exception.BaseDomainException;

public class ExceptionHttpStatusMapper {

    public static HttpStatusEnum map(Throwable exception) {
        if (exception instanceof MethodArgumentTypeMismatchException) {
            return HttpStatusEnum.BAD_REQUEST;
        } else if (exception instanceof TypeMismatchException ||
                exception.getCause() instanceof TypeMismatchException) {
            return HttpStatusEnum.BAD_REQUEST;
        } else if (exception instanceof BaseDomainException) {
            return HttpStatusEnum.NOT_FOUND;
        }
        return HttpStatusEnum.INTERNAL_SERVER_ERROR;
    }

}