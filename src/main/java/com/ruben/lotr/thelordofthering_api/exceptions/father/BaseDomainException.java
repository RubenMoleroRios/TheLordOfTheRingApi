package com.ruben.lotr.thelordofthering_api.exceptions.father;

public abstract class BaseDomainException extends RuntimeException {
    public BaseDomainException(String message) {
        super(message);
    }
}
