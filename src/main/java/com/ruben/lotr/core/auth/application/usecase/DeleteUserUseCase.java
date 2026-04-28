package com.ruben.lotr.core.auth.application.usecase;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.auth.domain.exception.UserNotFoundException;
import com.ruben.lotr.core.auth.domain.repository.UserRepositoryInterface;
import com.ruben.lotr.core.auth.domain.valueobject.UserIdVO;

@Service
public class DeleteUserUseCase {

    private final UserRepositoryInterface userRepository;

    public DeleteUserUseCase(UserRepositoryInterface userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String id) {
        UserIdVO userId = UserIdVO.create(id);
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(userId);
    }
}