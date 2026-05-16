package com.ruben.lotr.core.auth.domain.service;

import org.springframework.stereotype.Component;

@Component
public interface PasswordHasher {

    String hash(String plainPassword);

    boolean matches(String plainPassword, String hashedPassword);

}
