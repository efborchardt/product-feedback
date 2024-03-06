package com.efborchardt.productfeedback.domain._shared.notification;

public interface NotificationInterface {

    void addError(String message, String context);
    void throwErrorIfAny();
}
