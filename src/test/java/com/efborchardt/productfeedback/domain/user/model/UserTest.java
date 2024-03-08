package com.efborchardt.productfeedback.domain.user.model;

import com.efborchardt.productfeedback.domain._shared.notification.NotificationFactory;
import com.efborchardt.productfeedback.domain._shared.notification.NotificationInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private static NotificationInterface mockNotification;

    @BeforeAll
    static void beforeAll() {
        mockStatic(NotificationFactory.class, invocationOnMock -> mockNotification);
    }

    @Test
    void userIsValid() {
        new User("testUser", "test@example.com", "password", UserRole.USER);
        verify(mockNotification, never()).addError(anyString(), anyString());
    }

    @Test
    void userIsInvalid() {
        new User(null, "", "", "", UserRole.USER);
        verify(mockNotification).addError("ID is required", "User");
        verify(mockNotification).addError("Username is required", "User");
        verify(mockNotification).addError("email is required", "User");
        verify(mockNotification).addError("Password is required", "User");
    }

    @Test
    void changePasswordValidatesNewPassword() {
        User user = new User("testUser", "test@example.com", "oldPassword", UserRole.USER);
        user.changePassword("");
        verify(mockNotification).addError("Password is required", "User");
    }
}