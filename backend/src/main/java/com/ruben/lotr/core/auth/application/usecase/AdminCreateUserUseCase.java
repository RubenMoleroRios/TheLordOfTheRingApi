package com.ruben.lotr.core.auth.application.usecase;

import org.springframework.stereotype.Service;

import com.ruben.lotr.core.auth.application.valueobject.PlainPassword;
import com.ruben.lotr.core.auth.domain.exception.ForbiddenOperationException;
import com.ruben.lotr.core.auth.domain.exception.InvalidRoleException;
import com.ruben.lotr.core.auth.domain.exception.RoleNotFoundException;
import com.ruben.lotr.core.auth.domain.exception.UserAlreadyExistsException;
import com.ruben.lotr.core.auth.domain.model.Role;
import com.ruben.lotr.core.auth.domain.model.RoleName;
import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.repository.RoleRepositoryInterface;
import com.ruben.lotr.core.auth.domain.repository.UserRepositoryInterface;
import com.ruben.lotr.core.auth.domain.service.PasswordHasher;
import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserNameVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserPasswordHashVO;

@Service
public class AdminCreateUserUseCase {

    private final UserRepositoryInterface userRepository;
    private final RoleRepositoryInterface roleRepository;
    private final PasswordHasher passwordHasher;

    public AdminCreateUserUseCase(
            UserRepositoryInterface userRepository,
            RoleRepositoryInterface roleRepository,
            PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordHasher = passwordHasher;
    }

    public User execute(AdminCreateUserCommand command) {
        UserEmailVO email = UserEmailVO.create(command.email());
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new UserAlreadyExistsException(email.value());
        });

        RoleName targetRoleName = parseRole(command.role());
        validateAdminCreation(command.actorRole(), targetRoleName);

        Role role = roleRepository.findByName(targetRoleName)
                .orElseThrow(() -> new RoleNotFoundException(targetRoleName.name()));

        String hash = passwordHasher.hash(new PlainPassword(command.password()).value());

        User user = User.create(
                UserNameVO.create(command.name()),
                email,
                UserPasswordHashVO.fromHash(hash),
                role);

        userRepository.save(user);
        return user;
    }

    private RoleName parseRole(String rawRole) {
        try {
            return RoleName.from(rawRole);
        } catch (IllegalArgumentException ex) {
            throw new InvalidRoleException(rawRole);
        }
    }

    // La regla importante vive también aquí, no solo en la capa HTTP.
    private void validateAdminCreation(String actorRole, RoleName targetRole) {
        if (targetRole == RoleName.ADMIN && RoleName.from(actorRole) != RoleName.ADMIN) {
            throw new ForbiddenOperationException("Only administrators can create another administrator.");
        }
    }
}