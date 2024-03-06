package com.efborchardt.productfeedback.application.usecase.feedback.create;

import java.util.UUID;

public class CreateFeedbackRequestDTO {

    private UUID userId;
    private UUID productId;
    private String content;

    public CreateFeedbackRequestDTO(UUID userId, UUID productId, String content) {
        this.userId = userId;
        this.productId = productId;
        this.content = content;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
