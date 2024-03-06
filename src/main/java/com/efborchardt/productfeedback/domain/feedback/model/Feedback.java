package com.efborchardt.productfeedback.domain.feedback.model;

import com.efborchardt.productfeedback.domain._shared.notification.NotificationFactory;
import com.efborchardt.productfeedback.domain._shared.notification.NotificationInterface;
import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.user.model.User;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Feedback {

    private final UUID id;
    private String content;
    private final LocalDateTime submissionDate;
    private final User submittedBy;
    private final Product product;

    public Feedback(UUID id, String content, LocalDateTime submissionDate, User submittedBy, Product product) {
        this.id = id;
        this.content = content;
        this.submissionDate = submissionDate;
        this.submittedBy = submittedBy;
        this.product = product;
        validate();
    }

    public Feedback(String content, User submittedBy, Product product) {
        this.id = UUID.randomUUID();
        this.content = content;
        this.submissionDate = LocalDateTime.now();
        this.submittedBy = submittedBy;
        this.product = product;
        validate();
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public User getSubmittedBy() {
        return submittedBy;
    }

    public Product getProduct() {
        return product;
    }

    private void validate() {
        final NotificationInterface notification = NotificationFactory.create();
        final String context = "Feedback";

        if (this.id == null) {
            notification.addError("ID is required", context);
        }

        if (this.content == null || this.content.isBlank()) {
            notification.addError("Content is required", context);
        }

        if (this.submittedBy == null) {
            notification.addError("User is required", context);
        }

        if (this.product == null) {
            notification.addError("Product is required", context);
        }

        notification.throwErrorIfAny();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feedback feedback)) return false;
        return Objects.equals(id, feedback.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
