package com.example.SmartShop.configuration;

import com.example.SmartShop.constant.PredefinedRole;
import com.example.SmartShop.entity.User;
import com.example.SmartShop.exception.AppException;
import com.example.SmartShop.exception.ErrorCode;
import com.example.SmartShop.repository.RoleRepository;
import com.example.SmartShop.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RoleRepository roleRepository;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_EMAIL = "admin@example.com";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin1234@";



    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            log.info("ApplicationRunner is running...");
            if (userRepository.findByUserName(ADMIN_USER_NAME).isEmpty()) {
                log.info("Admin user not found, creating now...");
                User user = User.builder()
                        .userName(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .role(roleRepository.findByName(PredefinedRole.ADMIN_ROLE)
                                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)))
                        .email(ADMIN_EMAIL)
                        .gender("male")
                        .build();
                userRepository.save(user);
                log.warn("Admin user has been created with default password: admin1234@, please change it");
            } else {
                log.info("Admin user already exists, skipping creation.");
            }
        };
    }


}
