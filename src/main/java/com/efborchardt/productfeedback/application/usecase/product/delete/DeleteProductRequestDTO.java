package com.efborchardt.productfeedback.application.usecase.product.delete;

import java.util.UUID;

public class DeleteProductRequestDTO {

    private UUID productId;
    private String senderUsername;

    public DeleteProductRequestDTO(UUID productId, String senderUsername) {
        this.productId = productId;
        this.senderUsername = senderUsername;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }
}
