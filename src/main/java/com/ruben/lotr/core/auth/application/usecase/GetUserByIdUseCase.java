package com.ruben.lotr.core.auth.application.usecase;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.auth.domain.exception.UserNotFoundException;
import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.repository.UserRepositoryInterface;
import com.ruben.lotr.core.auth.domain.valueobject.UserIdVO;

@Service
public class GetUserByIdUseCase {

    private final UserRepositoryInterface userRepository;

    public GetUserByIdUseCase(UserRepositoryInterface userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String id) {
        UserIdVO userId = UserIdVO.create(id);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}