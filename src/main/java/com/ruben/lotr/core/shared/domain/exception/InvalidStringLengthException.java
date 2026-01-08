package com.ruben.lotr.core.shared.domain.exception;

import com.ruben.lotr.thelordofthering_api.exceptions.father.BaseDomainException;

public class InvalidStringLengthException extends BaseDomainException {
    public InvalidStringLengthException(int minLength, int maxLength, int actualLength) {
        super(String.format(
                "The length must be between <%d> and <%d> characters, this is the actual length <%d>.",
                minLength,
                maxLength,
                actualLength));
    }
}
