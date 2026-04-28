package com.ruben.lotr.core.auth.application.usecase;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.auth.application.valueobject.PlainPassword;
import com.ruben.lotr.core.auth.domain.exception.ForbiddenOperationException;
import com.ruben.lotr.core.auth.domain.exception.InvalidRoleException;
import com.ruben.lotr.core.auth.domain.exception.RoleNotFoundException;
import com.ruben.lotr.core.auth.domain.exception.UserAlreadyExistsException;
import com.ruben.lotr.core.auth.domain.exception.UserNotFoundException;
import com.ruben.lotr.core.auth.domain.model.Role;
import com.ruben.lotr.core.auth.domain.model.RoleName;
import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.repository.RoleRepositoryInterface;
import com.ruben.lotr.core.auth.domain.repository.UserRepositoryInterface;
import com.ruben.lotr.core.auth.domain.service.PasswordHasher;
import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserIdVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserNameVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserPasswordHashVO;

@Service
public class AdminUpdateUserUseCase {

    private final UserRepositoryInterface userRepository;
    private final RoleRepositoryInterface roleRepository;
    private final PasswordHasher passwordHasher;

    public AdminUpdateUserUseCase(
            UserRepositoryInterface userRepository,
            RoleRepositoryInterface roleRepository,
            PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordHasher = passwordHasher;
    }

    public User execute(AdminUpdateUserCommand command) {
        UserIdVO userId = UserIdVO.create(command.id());
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(command.id()));

        UserEmailVO newEmail = UserEmailVO.create(command.email());
        userRepository.findByEmail(newEmail)
                .filter(found -> !found.id().value().equals(existingUser.id().value()))
                .ifPresent(found -> {
                    throw new UserAlreadyExistsException(newEmail.value());
                });

        RoleName targetRoleName = parseRole(command.role());
        validateAdminCreation(command.actorRole(), targetRoleName);

        Role role = roleRepository.findByName(targetRoleName)
                .orElseThrow(() -> new RoleNotFoundException(targetRoleName.name()));

        String passwordHash = keepOrHashPassword(command.password(), existingUser.password().value());

        User updatedUser = User.rehydrate(
                existingUser.id(),
                UserNameVO.create(command.name()),
                newEmail,
                UserPasswordHashVO.fromHash(passwordHash),
                role);

        userRepository.save(updatedUser);
        return updatedUser;
    }

    private RoleName parseRole(String rawRole) {
        try {
            return RoleName.from(rawRole);
        } catch (IllegalArgumentException ex) {
            throw new InvalidRoleException(rawRole);
        }
    }

    private void validateAdminCreation(String actorRole, RoleName targetRole) {
        if (targetRole == RoleName.ADMIN && RoleName.from(actorRole) != RoleName.ADMIN) {
            throw new ForbiddenOperationException("Only administrators can assign the administrator role.");
        }
    }

    // Si no llega password, preservamos el hash actual y evitamos rehash
    // innecesario.
    private String keepOrHashPassword(String rawPassword, String currentHash) {
        if (rawPassword == null || rawPassword.isBlank()) {
            return currentHash;
        }

        return passwordHasher.hash(new PlainPassword(rawPassword).value());
    }
}