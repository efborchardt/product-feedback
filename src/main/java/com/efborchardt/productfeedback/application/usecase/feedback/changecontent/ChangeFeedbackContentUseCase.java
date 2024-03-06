package com.efborchardt.productfeedback.application.usecase.feedback.changecontent;

import com.efborchardt.productfeedback.domain.feedback.model.Feedback;
import com.efborchardt.productfeedback.domain.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChangeFeedbackContentUseCase {

    private final FeedbackService service;

    @Autowired
    public ChangeFeedbackContentUseCase(FeedbackService service) {
        this.service = service;
    }

    public ChangeFeedbackContentResponseDTO execute(UUID feedbackId, ChangeFeedbackContentRequestDTO request) {
        final Feedback updatedFeedback = this.service.changeContent(feedbackId, request.getContent());
        return new ChangeFeedbackContentResponseDTO(
                updatedFeedback.getProduct().getName(),
                updatedFeedback.getContent()
        );
    }
}
