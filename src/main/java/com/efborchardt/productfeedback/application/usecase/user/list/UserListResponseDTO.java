package com.efborchardt.productfeedback.application.usecase.user.list;

import java.util.List;

public class UserListResponseDTO {

    List<UserResponseDTO> users;

    public UserListResponseDTO(List<UserResponseDTO> users) {
        this.users = users;
    }

    public List<UserResponseDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponseDTO> users) {
        this.users = users;
    }
}
