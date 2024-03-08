package com.efborchardt.productfeedback.domain.feedback.repository;

import com.efborchardt.productfeedback.domain.feedback.model.Feedback;
import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.model.UserRole;
import com.efborchardt.productfeedback.infrastructure.persistence.feedback.model.FeedbackEntity;
import com.efborchardt.productfeedback.infrastructure.persistence.feedback.repository.FeedbackRepositoryImpl;
import com.efborchardt.productfeedback.infrastructure.persistence.user.model.UserEntity;
import com.efborchardt.productfeedback.infrastructure.persistence.product.model.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(FeedbackRepositoryImpl.class)
class FeedbackRepositoryTest {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user;
    private Product product;

    @BeforeEach
    public void setup() {
        UserEntity userEntity = new UserEntity(
            UUID.randomUUID(),
            "testUser",
            "test@example.com",
            "testPassword",
                UserRole.ADMIN
        );

        entityManager.persist(userEntity);
        user = new User(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRole()
        );

        ProductEntity productEntity = new ProductEntity(
                UUID.randomUUID(),
                "testProduct",
                "testDescription",
                BigDecimal.valueOf(25.99),
                userEntity
        );

        entityManager.persist(productEntity);
        product = new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                user
        );
    }

    @Test
    public void testSave() {
        UUID feedbackId = UUID.randomUUID();
        Feedback feedback = new Feedback(feedbackId, "Great product", LocalDateTime.now(), user, product);

        feedbackRepository.save(feedback);

        FeedbackEntity foundFeedbackEntity = entityManager.find(FeedbackEntity.class, feedbackId);
        assertThat(foundFeedbackEntity).isNotNull();
        assertThat(foundFeedbackEntity.getContent()).isEqualTo("Great product");
        assertThat(foundFeedbackEntity.getSubmittedBy().getId()).isEqualTo(user.getId());
        assertThat(foundFeedbackEntity.getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    public void testFindById_Found() {
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setId(UUID.randomUUID());
        feedbackEntity.setContent("Feedback content");
        feedbackEntity.setSubmissionDate(LocalDateTime.now());
        feedbackEntity.setSubmittedBy(entityManager.find(UserEntity.class, user.getId()));
        feedbackEntity.setProduct(entityManager.find(ProductEntity.class, product.getId()));
        entityManager.persistAndFlush(feedbackEntity);

        Optional<Feedback> foundFeedback = feedbackRepository.findById(feedbackEntity.getId());

        assertThat(foundFeedback).isPresent();
        foundFeedback.ifPresent(feedback -> {
            assertThat(feedback.getId()).isEqualTo(feedbackEntity.getId());
            assertThat(feedback.getContent()).isEqualTo(feedbackEntity.getContent());
            assertThat(feedback.getSubmittedBy().getId()).isEqualTo(user.getId());
            assertThat(feedback.getProduct().getId()).isEqualTo(product.getId());
        });
    }
}