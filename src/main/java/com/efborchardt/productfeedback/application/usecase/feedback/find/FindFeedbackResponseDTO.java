package com.efborchardt.productfeedback.application.usecase.feedback.find;

import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.user.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class FindFeedbackResponseDTO {

    private UUID id;
    private String content;
    private LocalDateTime submissionDate;
    private User submittedBy;
    private Product product;

    public FindFeedbackResponseDTO(UUID id, String content, LocalDateTime submissionDate, User submittedBy, Product product) {
        this.id = id;
        this.content = content;
        this.submissionDate = submissionDate;
        this.submittedBy = submittedBy;
        this.product = product;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public User getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(User submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
