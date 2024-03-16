package com.efborchardt.productfeedback.domain.user.model;

import com.efborchardt.productfeedback.domain._shared.notification.NotificationFactory;
import com.efborchardt.productfeedback.domain._shared.notification.NotificationInterface;
import com.efborchardt.productfeedback.domain._shared.validator.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class User implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void changePassword(String password) {
        this.password = password;
        validate();
    }

    public UUID getId() {
        return id;
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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
