package com.efborchardt.productfeedback.domain._shared.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {
    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
    }

    @Test
    void addErrorAndCheckErrorPresence() {
        assertFalse(notification.hasErrors(), "Initially, hasErrors should return false");
        notification.addError("Error 1", "Context 1");
        assertTrue(notification.hasErrors(), "After adding an error, hasErrors should return true");
    }

    @Test
    void errorMessageAfterAddingErrors() {
        notification.addError("Error 1", "Context 1");
        notification.addError("Error 2", "Context 2");
        String expectedMessage = "Context 1: Error 1, Context 2: Error 2";
        assertEquals(expectedMessage, notification.errorMessage(), "The errorMessage should match the expected format and content");
    }

    @Test
    void errorMessageIsEmptyWhenNoErrorsAdded() {
        assertTrue(notification.errorMessage().isEmpty(), "errorMessage should be empty when no errors have been added");
    }

    @Test
    void hasErrorsReturnsFalseWhenNoErrorsAdded() {
        assertFalse(notification.hasErrors(), "hasErrors should return false when no errors have been added");
    }

    @Test
    void hasErrorsReturnsTrueWhenErrorsAdded() {
        notification.addError("Test Error", "Test Context");
        assertTrue(notification.hasErrors(), "hasErrors should return true when an error has been added");
    }

    @Test
    void throwErrorIfAnyDoesNotThrowWhenNoErrors() {
        assertDoesNotThrow(() -> notification.throwErrorIfAny(),
                "throwErrorIfAny should not throw an exception when no errors have been added");
    }

    @Test
    void throwErrorIfAnyThrowsWhenErrorsPresent() {
        notification.addError("Error 1", "Context 1");
        Exception exception = assertThrows(IllegalStateException.class, () -> notification.throwErrorIfAny(),
                "throwErrorIfAny should throw an IllegalStateException when errors are present");
        String expectedMessage = "Context 1: Error 1";
        assertEquals(expectedMessage, exception.getMessage());
    }
}