package com.efborchardt.productfeedback.application.usecase.feedback.create;

import com.efborchardt.productfeedback.domain.feedback.model.Feedback;
import com.efborchardt.productfeedback.domain.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateFeedbackUseCase {

    private final FeedbackService service;

    @Autowired
    public CreateFeedbackUseCase(FeedbackService service) {
        this.service = service;
    }

    public CreateFeedbackResponseDTO execute(CreateFeedbackRequestDTO request) {
        final Feedback createdFeedback = this.service.createNewFeedback(
                request.getUserId(),
                request.getProductId(),
                request.getContent()
        );

        return new CreateFeedbackResponseDTO(
                createdFeedback.getId(),
                createdFeedback.getContent(),
                createdFeedback.getSubmissionDate(),
                createdFeedback.getSubmittedBy().getUsername(),
                createdFeedback.getProduct().getName()
        );
    }
}
