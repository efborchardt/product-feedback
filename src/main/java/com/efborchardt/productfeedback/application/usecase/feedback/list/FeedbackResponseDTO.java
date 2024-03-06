package com.efborchardt.productfeedback.application.usecase.feedback.list;

import java.time.LocalDateTime;
import java.util.UUID;

public class FeedbackResponseDTO {

    private UUID id;
    private String content;
    private LocalDateTime submissionDate;
    private String submittedBy;
    private String productName;

    public FeedbackResponseDTO(UUID id, String content, LocalDateTime submissionDate, String submittedBy, String productName) {
        this.id = id;
        this.content = content;
        this.submissionDate = submissionDate;
        this.submittedBy = submittedBy;
        this.productName = productName;
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

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
