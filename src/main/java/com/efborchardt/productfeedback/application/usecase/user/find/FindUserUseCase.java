package com.efborchardt.productfeedback.application.usecase.user.find;

import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FindUserUseCase {

    private final UserService userService;

    @Autowired
    public FindUserUseCase(UserService userService) {
        this.userService = userService;
    }

    public FindUserResponseDTO execute(UUID id) {
        final User user = this.userService.findById(id);
        return new FindUserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
