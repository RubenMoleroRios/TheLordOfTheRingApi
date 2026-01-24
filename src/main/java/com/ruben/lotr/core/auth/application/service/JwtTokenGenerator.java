package com.ruben.lotr.core.auth.application.service;

import com.ruben.lotr.core.auth.domain.model.User;

import org.springframework.stereotype.Service;

@Service
public interface JwtTokenGenerator {
    String generate(User user);
}
