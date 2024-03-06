package com.efborchardt.productfeedback.application.usecase.feedback.find;

import com.efborchardt.productfeedback.domain.feedback.model.Feedback;
import com.efborchardt.productfeedback.domain.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FindFeedbackUseCase {

    private final FeedbackService service;

    @Autowired
    public FindFeedbackUseCase(FeedbackService service) {
        this.service = service;
    }

    public FindFeedbackResponseDTO execute(UUID feedbackId) {
        final Feedback feedback = this.service.findById(feedbackId);
        return new FindFeedbackResponseDTO(
                feedback.getId(),
                feedback.getContent(),
                feedback.getSubmissionDate(),
                feedback.getSubmittedBy(),
                feedback.getProduct()
        );
    }
}
