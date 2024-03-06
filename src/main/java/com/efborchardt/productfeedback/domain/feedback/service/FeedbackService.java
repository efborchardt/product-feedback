package com.efborchardt.productfeedback.domain.feedback.service;

import com.efborchardt.productfeedback.domain.feedback.exception.FeedbackNotFoundException;
import com.efborchardt.productfeedback.domain.feedback.model.Feedback;
import com.efborchardt.productfeedback.domain.feedback.repository.FeedbackRepository;
import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.product.repository.ProductRepository;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Feedback createNewFeedback(UUID userId, UUID productId, String content) {
        final User user = this.userRepository.findById(userId).orElse(null);
        final Product product = this.productRepository.findById(productId).orElse(null);

        final Feedback feedback = new Feedback(
                content,
                user,
                product
        );

        this.feedbackRepository.save(feedback);

        return feedback;
    }

    public Feedback findById(UUID id) {
        return this.feedbackRepository.findById(id)
                .orElseThrow(() -> new FeedbackNotFoundException("Unable to find a user with ID " + id.toString()));
    }

    public Feedback changeContent(UUID feedbackId, String content) {
        final Feedback feedback = findById(feedbackId);
        feedback.changeContent(content);

        this.feedbackRepository.save(feedback);

        return feedback;
    }

    public List<Feedback> listByProduct(UUID productId) {
        return this.feedbackRepository.findByProductId(productId);
    }

    public List<Feedback> listByUser(UUID userId) {
        return this.feedbackRepository.findByUserId(userId);
    }
}
