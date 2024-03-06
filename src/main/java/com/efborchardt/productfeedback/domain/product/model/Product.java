package com.efborchardt.productfeedback.domain.product.model;

import com.efborchardt.productfeedback.domain._shared.notification.NotificationFactory;
import com.efborchardt.productfeedback.domain._shared.notification.NotificationInterface;
import com.efborchardt.productfeedback.domain.user.model.User;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Product {

    private final UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private User createdBy;

    public Product(UUID id, String name, String description, BigDecimal price, User createdBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdBy = createdBy;
        validate();
    }

    public Product(String name, String description, BigDecimal price, User createdBy) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdBy = createdBy;
        validate();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    private void validate() {
        final NotificationInterface notification = NotificationFactory.create();
        final String context = "Product";

        if (this.id == null) {
            notification.addError("ID is required", context);
        }

        if (this.name == null || this.name.isBlank()) {
            notification.addError("Name is required", context);
        }

        if (this.price == null || this.price.compareTo(BigDecimal.ZERO) <= 0) {
            notification.addError("Price must be greater than zero", context);
        }

        if (this.createdBy == null) {
            notification.addError("The product must be linked to a user", context);
        }

        notification.throwErrorIfAny();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
