package com.efborchardt.productfeedback.application.usecase.user.changepassword;

import com.efborchardt.productfeedback.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChangePasswordUseCase {

    private final UserService service;

    @Autowired
    public ChangePasswordUseCase(UserService service) {
        this.service = service;
    }

    public ChangePasswordResponseDTO execute(UUID userId, String newPassword) {
        this.service.changePassword(userId, newPassword);
        return new ChangePasswordResponseDTO();
    }
}
