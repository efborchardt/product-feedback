package com.efborchardt.productfeedback.application.usecase.product.create;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateProductRequestDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private UUID userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
