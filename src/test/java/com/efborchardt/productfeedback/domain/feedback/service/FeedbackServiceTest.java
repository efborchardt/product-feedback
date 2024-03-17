package com.efborchardt.productfeedback.domain.feedback.service;

import com.efborchardt.productfeedback.domain.feedback.exception.FeedbackNotFoundException;
import com.efborchardt.productfeedback.domain.feedback.model.Feedback;
import com.efborchardt.productfeedback.domain.feedback.repository.FeedbackRepository;
import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.product.repository.ProductRepository;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.model.UserRole;
import com.efborchardt.productfeedback.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    private final UUID userId = UUID.randomUUID();
    private final UUID productId = UUID.randomUUID();
    private final UUID feedbackId = UUID.randomUUID();

    private final User user = new User(
            userId,
            "test",
            "email@email.com",
            "password",
            UserRole.USER
    );
    private final Product product = new Product(
            productId,
            "Product name",
            "Product description",
            BigDecimal.valueOf(10.96),
            user
    );

    private final Feedback feedback = new Feedback(
            feedbackId,
            "content",
            LocalDateTime.now(),
            user,
            product
    );

    @Test
    void createNewFeedbackSuccessfully() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(feedbackRepository).save(any(Feedback.class));

        Feedback createdFeedback = feedbackService.createNewFeedback(userId, productId, "Content");

        assertNotNull(createdFeedback);
        verify(feedbackRepository).save(any(Feedback.class));
        verify(userRepository).findById(userId);
        verify(productRepository).findById(productId);
    }

    @Test
    void findByIdSuccessfully() {
        when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));

        Feedback foundFeedback = feedbackService.findById(feedbackId);

        assertNotNull(foundFeedback);
        assertEquals(feedbackId, foundFeedback.getId());
    }

    @Test
    void findByIdThrowsExceptionWhenNotFound() {
        when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.empty());

        assertThrows(FeedbackNotFoundException.class, () -> feedbackService.findById(feedbackId));
    }

    @Test
    void changeContentSuccessfully() {
        when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));
        doNothing().when(feedbackRepository).save(any(Feedback.class));

        Feedback updatedFeedback = feedbackService.changeContent(feedbackId, "Updated content");

        assertNotNull(updatedFeedback);
        assertEquals("Updated content", updatedFeedback.getContent());
    }

    @Test
    void listByProductReturnsFeedbackList() {
        when(feedbackRepository.findByProductId(productId)).thenReturn(List.of(feedback));

        List<Feedback> feedbackList = feedbackService.listByProduct(productId);

        assertFalse(feedbackList.isEmpty());
        assertEquals(1, feedbackList.size());
    }

    @Test
    void listByUserReturnsFeedbackList() {
        when(feedbackRepository.findByUserId(userId)).thenReturn(List.of(feedback));

        List<Feedback> feedbackList = feedbackService.listByUser(userId);

        assertFalse(feedbackList.isEmpty());
        assertEquals(1, feedbackList.size());
    }
}