package com.efborchardt.productfeedback.application.usecase.user.create;

import com.efborchardt.productfeedback.domain.user.model.UserRole;

public class CreateUserRequestDTO {

    private String username;
    private String password;
    private String email;

    private UserRole role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
