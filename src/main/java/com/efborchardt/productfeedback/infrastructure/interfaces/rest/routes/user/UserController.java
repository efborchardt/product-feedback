package com.efborchardt.productfeedback.infrastructure.interfaces.rest.routes.user;

import com.efborchardt.productfeedback.application.usecase.user.changepassword.ChangePasswordRequestDTO;
import com.efborchardt.productfeedback.application.usecase.user.changepassword.ChangePasswordResponseDTO;
import com.efborchardt.productfeedback.application.usecase.user.changepassword.ChangePasswordUseCase;
import com.efborchardt.productfeedback.application.usecase.user.create.CreateUserRequestDTO;
import com.efborchardt.productfeedback.application.usecase.user.create.CreateUserResponseDTO;
import com.efborchardt.productfeedback.application.usecase.user.create.CreateUserUseCase;
import com.efborchardt.productfeedback.application.usecase.user.find.FindUserResponseDTO;
import com.efborchardt.productfeedback.application.usecase.user.find.FindUserUseCase;
import com.efborchardt.productfeedback.application.usecase.user.list.ListAllUsersUseCase;
import com.efborchardt.productfeedback.application.usecase.user.list.UserListResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final ListAllUsersUseCase listAllUsersUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final FindUserUseCase findUserUseCase;

    @Autowired
    public UserController(CreateUserUseCase createUserUseCase,
                          ListAllUsersUseCase listAllUsersUseCase,
                          ChangePasswordUseCase changePasswordUseCase,
                          FindUserUseCase findUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.listAllUsersUseCase = listAllUsersUseCase;
        this.changePasswordUseCase = changePasswordUseCase;
        this.findUserUseCase = findUserUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO request) {
        CreateUserResponseDTO response = this.createUserUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<UserListResponseDTO> findAllUsers() {
        UserListResponseDTO response = this.listAllUsersUseCase.execute();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindUserResponseDTO> findById(UUID id) {
        FindUserResponseDTO response = this.findUserUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}/change-password")
    public ResponseEntity<ChangePasswordResponseDTO> changePassword(@PathVariable UUID userId, @RequestBody ChangePasswordRequestDTO request) {
        final ChangePasswordResponseDTO response = this.changePasswordUseCase.execute(userId, request.getNewPassword());
        return ResponseEntity.ok(response);
    }
}
