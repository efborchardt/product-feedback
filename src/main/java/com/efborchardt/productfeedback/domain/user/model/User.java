package com.efborchardt.productfeedback.domain.user.model;

import com.efborchardt.productfeedback.domain._shared.notification.NotificationFactory;
import com.efborchardt.productfeedback.domain._shared.notification.NotificationInterface;
import com.efborchardt.productfeedback.domain._shared.validator.EmailValidator;

import java.util.Objects;
import java.util.UUID;

public class User {
    private final UUID id;
    private String username;
    private String email;
    private String password;
    private final UserRole role;

    public User(String username, String email, String password, UserRole role) {
        this.role = role;
        this.id = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.password = password;
        validate();
    }

    public User(UUID id, String username, String email, String password, UserRole role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        validate();
    }

    public void changePassword(String password) {
        this.password = password;
        validate();
    }

    public void changeUsername(String username) {
        this.username = username;
        validate();
    }

    public void changeEmail(String email) {
        this.email = email;
        validate();
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    private void validate() {
        final NotificationInterface notification = NotificationFactory.create();
        final String context = "User";

        if (this.id == null) {
            notification.addError("ID is required", context);
        }

        if (this.username == null || this.username.isBlank()) {
            notification.addError("Username is required", context);
        }

        if (this.email == null || this.email.isBlank()) {
            notification.addError("email is required", context);
        }

        if (this.email != null && !EmailValidator.isValid(this.email)) {
            notification.addError("Not a valid email", context);
        }

        if (this.password == null || this.password.isBlank()) {
            notification.addError("Password is required", context);
        }

        notification.throwErrorIfAny();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
