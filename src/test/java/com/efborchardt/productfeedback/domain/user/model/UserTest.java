package com.efborchardt.productfeedback.domain.user.model;

import com.efborchardt.productfeedback.domain._shared.notification.NotificationFactory;
import com.efborchardt.productfeedback.domain._shared.notification.NotificationInterface;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserTest {

    private static MockedStatic<NotificationFactory> mockedStatic;

    @Mock
    private static NotificationInterface mockNotification;

    @BeforeAll
    static void beforeAll() {
        mockedStatic = mockStatic(NotificationFactory.class, invocationOnMock -> mockNotification);
    }

    @AfterAll
    static void afterAll() {
        mockedStatic.close();
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