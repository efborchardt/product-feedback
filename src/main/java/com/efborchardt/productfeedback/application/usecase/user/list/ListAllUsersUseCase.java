package com.efborchardt.productfeedback.application.usecase.user.list;

import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListAllUsersUseCase {

    private final UserService service;

    @Autowired
    public ListAllUsersUseCase(UserService service) {
        this.service = service;
    }

    public UserListResponseDTO execute() {
        return mapUserListToDto(this.service.listAllUsers());
    }

    private UserListResponseDTO mapUserListToDto(List<User> userList) {
        List<UserResponseDTO> reponseUserList = userList.stream()
                .map(this::mapUserToDto)
                .collect(Collectors.toList());

        return new UserListResponseDTO(reponseUserList);
    }

    private UserResponseDTO mapUserToDto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
