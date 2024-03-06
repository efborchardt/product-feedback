package com.efborchardt.productfeedback.domain._shared.notification;

public class NotificationFactory {
    public static NotificationInterface create() {
        return new Notification();
    }
}
