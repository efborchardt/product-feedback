package com.efborchardt.productfeedback.domain._shared.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Notification implements NotificationInterface {

    private final List<NotificationError> errors = new ArrayList<>();

    public void addError(String message, String context) {
        errors.add(new NotificationError(message, context));
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public String errorMessage() {
        return errors.stream()
                .map(error -> error.context() + ": " + error.message())
                .collect(Collectors.joining(", "));
    }

    public void throwErrorIfAny() {
        if (hasErrors()) {
            throw new IllegalStateException(errorMessage());
        }
    }
}
