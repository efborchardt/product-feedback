package com.efborchardt.productfeedback.domain.user.service;

import com.efborchardt.productfeedback.domain.user.exception.UserNotFoundException;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.model.UserRole;
import com.efborchardt.productfeedback.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final User user = new User(
            "test",
            "email@email.com",
            "password",
            UserRole.USER
    );

    @Test
    void createNewUserSuccessfully() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        userService.createNewUser(user);
        verify(userRepository).save(user);
    }

    @Test
    void createNewUserFailsWhenUsernameExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        assertThrows(RuntimeException.class, () -> userService.createNewUser(user), "Username already exists");
    }

    @Test
    void listAllUsersSuccessfully() {
        when(userRepository.list()).thenReturn(List.of(user));
        List<User> users = userService.listAllUsers();
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        verify(userRepository).list();
    }

    @Test
    void changePasswordSuccessfully() {
        UUID userId = user.getId();
        String newPassword = "newPassword";
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        userService.changePassword(userId, newPassword);
        verify(userRepository).save(user);
    }

    @Test
    void changePasswordFailsWhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.changePassword(userId, "newPassword"));
    }

    @Test
    void findByIdSuccessfully() {
        UUID userId = user.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        User foundUser = userService.findById(userId);
        assertEquals(user, foundUser);
    }

    @Test
    void findByIdFailsWhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
    }

    @Test
    void findByUsernameSuccessfully() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        User foundUser = userService.findByUsername(user.getUsername());
        assertEquals(user, foundUser);
    }

    @Test
    void findByUsernameFailsWhenUserNotFound() {
        when(userRepository.findByUsername("nonExistingUsername")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findByUsername("nonExistingUsername"));
    }
}