package com.efborchardt.productfeedback.domain.feedback.exception;

public class FeedbackNotFoundException extends RuntimeException {
    public FeedbackNotFoundException(String message) {
        super(message);
    }
}
