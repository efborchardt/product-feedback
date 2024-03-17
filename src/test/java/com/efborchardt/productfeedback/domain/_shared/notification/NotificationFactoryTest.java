package com.efborchardt.productfeedback.domain._shared.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {

    @Test
    void createReturnsInstanceOfNotification() {
        NotificationInterface notification = NotificationFactory.create();
        assertNotNull(notification, "create() should return a non-null instance");
        assertInstanceOf(Notification.class, notification, "create() should return an instance of Notification");
    }
}