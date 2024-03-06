package com.efborchardt.productfeedback.infrastructure.persistence.feedback.model;

import com.efborchardt.productfeedback.infrastructure.persistence.product.model.ProductEntity;
import com.efborchardt.productfeedback.infrastructure.persistence.user.model.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "feedback")
public class FeedbackEntity {

    @Id
    private UUID id;
    private String content;
    private LocalDateTime submissionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity submittedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public FeedbackEntity() {}

    public FeedbackEntity(UUID id, String content, LocalDateTime submissionDate, UserEntity submittedBy, ProductEntity product) {
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

    public UserEntity getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(UserEntity submittedBy) {
        this.submittedBy = submittedBy;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
