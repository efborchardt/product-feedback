package com.efborchardt.productfeedback.application.usecase.user.create;

import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.model.UserRole;
import com.efborchardt.productfeedback.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateUserUseCase {

    private final UserService service;

    @Autowired
    public CreateUserUseCase(UserService service) {
        this.service = service;
    }

    public CreateUserResponseDTO execute(CreateUserRequestDTO request) {
        final String encryptedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
        final User user = new User(
                request.getUsername(),
                request.getEmail(),
                encryptedPassword,
                request.getRole()
        );

        final User createdUser = this.service.createNewUser(user);

        return new CreateUserResponseDTO(
                createdUser.getId().toString(),
                createdUser.getUsername(),
                createdUser.getPassword(),
                createdUser.getEmail()
        );
    }
}
