package com.efborchardt.productfeedback.domain.feedback.model;

import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    private final User user = new User(
            "test",
            "email@email.com",
            "password",
            UserRole.USER
    );
    private final Product product = new Product(
            "Product name",
            "Product description",
            BigDecimal.valueOf(10.96),
            user
    );
    private LocalDateTime submissionDate;
    private UUID id;

    @BeforeEach
    void setUp() {
        submissionDate = LocalDateTime.now();
        id = UUID.randomUUID();
    }

    @Test
    void testNoIdConstructor() {
        Feedback feedback = new Feedback("content", user, product);
        assertNotNull(feedback.getId(), "The Id must be created by the Feedback class");
        assertNotNull(feedback.getSubmissionDate(), "SubmissionDate date should be defined by the Feedback class");
        assertEquals("content", feedback.getContent());
        assertEquals(user, feedback.getSubmittedBy());
        assertEquals(product, feedback.getProduct());
    }

    @Test
    void createValidFeedback() {
        assertDoesNotThrow(() -> new Feedback(id, "Valid content", submissionDate, user, product),
                "Creating a feedback with all valid fields should not throw an exception.");
    }

    @Test
    void changeContentUpdatesContent() {
        Feedback feedback = new Feedback(id, "Initial content", submissionDate, user, product);
        String newContent = "Updated content";
        feedback.changeContent(newContent);
        assertEquals(newContent, feedback.getContent(), "changeContent should update the feedback's content.");
    }

    @Test
    void equalsAndHashCode() {
        Feedback feedback1 = new Feedback(id, "Content 1", submissionDate, user, product);
        Feedback feedback2 = new Feedback(id, "Content 2", submissionDate.plusDays(1), user, product);
        assertEquals(feedback1, feedback2, "Feedback entities with the same ID should be equal.");
        assertEquals(feedback1.hashCode(), feedback2.hashCode(), "Feedback entities with the same ID should have the same hashCode.");
    }

    @Test
    void feedbackWithoutIdThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, () -> new Feedback(null, "Valid content", submissionDate, user, product),
                "Feedback without an ID should throw an exception.");
        assertTrue(exception.getMessage().contains("ID is required"), "Exception message should indicate missing ID.");
    }

    @Test
    void feedbackWithoutContentThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, () -> new Feedback(id, null, submissionDate, user, product),
                "Feedback without content should throw an exception.");
        assertTrue(exception.getMessage().contains("Content is required"), "Exception message should indicate missing content.");
    }

    @Test
    void feedbackWithBlankContentThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, () -> new Feedback(id, " ", submissionDate, user, product),
                "Feedback with blank content should throw an exception.");
        assertTrue(exception.getMessage().contains("Content is required"), "Exception message should indicate missing content.");
    }

    @Test
    void feedbackWithoutUserThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, () -> new Feedback(id, "Valid content", submissionDate, null, product),
                "Feedback without a user should throw an exception.");
        assertTrue(exception.getMessage().contains("User is required"), "Exception message should indicate missing user.");
    }

    @Test
    void feedbackWithoutProductThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, () -> new Feedback(id, "Valid content", submissionDate, user, null),
                "Feedback without a product should throw an exception.");
        assertTrue(exception.getMessage().contains("Product is required"), "Exception message should indicate missing product.");
    }

    @Test
    void feedbackWithMultipleValidationErrors() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Feedback(null, " ", null, null, null),
                "Feedback violating multiple validation rules should throw an exception with all error messages concatenated.");

        String errorMessage = exception.getMessage();

        assertTrue(errorMessage.contains("ID is required"), "Error message should include missing ID error.");
        assertTrue(errorMessage.contains("Content is required"), "Error message should include missing content error.");
        assertTrue(errorMessage.contains("User is required"), "Error message should include missing user error.");
        assertTrue(errorMessage.contains("Product is required"), "Error message should include missing product error.");
    }
}