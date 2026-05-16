package com.ruben.lotr.core.auth.domain.exception;

import com.ruben.lotr.core.shared.domain.exception.BaseDomainException;

public final class RoleNotFoundException extends BaseDomainException {

    public RoleNotFoundException(String roleName) {
        super("Role not found: " + roleName);
    }
}