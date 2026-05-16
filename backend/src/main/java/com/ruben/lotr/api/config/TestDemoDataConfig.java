package com.ruben.lotr.api.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.model.RoleName;
import com.ruben.lotr.core.auth.domain.repository.RoleRepositoryInterface;
import com.ruben.lotr.core.auth.domain.repository.UserRepositoryInterface;
import com.ruben.lotr.core.auth.domain.service.PasswordHasher;
import com.ruben.lotr.core.auth.domain.exception.RoleNotFoundException;
import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserNameVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserPasswordHashVO;

@Configuration
@Profile("test")
public class TestDemoDataConfig {

    private static final String DEMO_EMAIL = "demo@lotr.local";
    private static final String DEMO_NAME = "Demo Baggins";
    private static final String DEMO_PASSWORD = "mellon123";

    @Bean
    public ApplicationRunner demoUserInitializer(UserRepositoryInterface userRepository,
            RoleRepositoryInterface roleRepository,
            PasswordHasher passwordHasher) {
        return args -> {
            UserEmailVO demoEmail = UserEmailVO.create(DEMO_EMAIL);

            if (userRepository.findByEmail(demoEmail).isPresent()) {
                return;
            }

            User demoUser = User.create(
                    UserNameVO.create(DEMO_NAME),
                    demoEmail,
                    UserPasswordHashVO.fromHash(passwordHasher.hash(DEMO_PASSWORD)),
                    roleRepository.findByName(RoleName.USER)
                            .orElseThrow(() -> new RoleNotFoundException(RoleName.USER.name())));

            userRepository.save(demoUser);
        };
    }
}