package com.ruben.lotr.core.auth.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.repository.UserRepositoryInterface;

@Service
public class GetUsersUseCase {

    private final UserRepositoryInterface userRepository;

    public GetUsersUseCase(UserRepositoryInterface userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute() {
        return userRepository.findAll();
    }
}