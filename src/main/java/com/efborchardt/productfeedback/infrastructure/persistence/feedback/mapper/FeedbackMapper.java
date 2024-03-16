package com.efborchardt.productfeedback.infrastructure.persistence.feedback.mapper;

import com.efborchardt.productfeedback.domain.feedback.model.Feedback;
import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.model.UserRole;
import com.efborchardt.productfeedback.infrastructure.persistence.feedback.model.FeedbackEntity;
import com.efborchardt.productfeedback.infrastructure.persistence.product.model.ProductEntity;
import com.efborchardt.productfeedback.infrastructure.persistence.user.model.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class FeedbackMapper {

    private FeedbackMapper() {}

    public static FeedbackEntity mapToPersistence(Feedback feedback) {
        return new FeedbackEntity(
                feedback.getId(),
                feedback.getContent(),
                feedback.getSubmissionDate(),
                new UserEntity(
                        feedback.getSubmittedBy().getId(),
                        feedback.getSubmittedBy().getUsername(),
                        feedback.getSubmittedBy().getEmail(),
                        feedback.getSubmittedBy().getPassword(),
                        feedback.getSubmittedBy().getRole().asString()
                ),
                new ProductEntity(
                        feedback.getProduct().getId(),
                        feedback.getProduct().getName(),
                        feedback.getProduct().getDescription(),
                        feedback.getProduct().getPrice(),
                        new UserEntity(
                                feedback.getProduct().getCreatedBy().getId(),
                                feedback.getProduct().getCreatedBy().getUsername(),
                                feedback.getProduct().getCreatedBy().getEmail(),
                                feedback.getProduct().getCreatedBy().getPassword(),
                                feedback.getProduct().getCreatedBy().getRole().asString()
                        )
                )
        );
    }

    public static Feedback mapToDomain(FeedbackEntity feedbackEntity) {
        return new Feedback(
                feedbackEntity.getId(),
                feedbackEntity.getContent(),
                feedbackEntity.getSubmissionDate(),
                new User(
                        feedbackEntity.getSubmittedBy().getId(),
                        feedbackEntity.getSubmittedBy().getUsername(),
                        feedbackEntity.getSubmittedBy().getEmail(),
                        feedbackEntity.getSubmittedBy().getPassword(),
                        UserRole.fromString(feedbackEntity.getSubmittedBy().getRole())
                ),
                new Product(
                        feedbackEntity.getProduct().getId(),
                        feedbackEntity.getProduct().getName(),
                        feedbackEntity.getProduct().getDescription(),
                        feedbackEntity.getProduct().getPrice(),
                        new User(
                                feedbackEntity.getProduct().getCreatedBy().getId(),
                                feedbackEntity.getProduct().getCreatedBy().getUsername(),
                                feedbackEntity.getProduct().getCreatedBy().getEmail(),
                                feedbackEntity.getProduct().getCreatedBy().getPassword(),
                                UserRole.fromString(feedbackEntity.getProduct().getCreatedBy().getRole())
                        )
                )
        );
    }

    public static List<Feedback> mapListToDomain(List<FeedbackEntity> feedbackEntityList) {
        return feedbackEntityList.stream()
                .map(FeedbackMapper::mapToDomain)
                .collect(Collectors.toList());
    }
}
