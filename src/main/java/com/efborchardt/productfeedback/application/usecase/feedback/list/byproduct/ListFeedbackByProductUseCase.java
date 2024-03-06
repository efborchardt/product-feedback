package com.efborchardt.productfeedback.application.usecase.feedback.list.byproduct;

import com.efborchardt.productfeedback.application.usecase.feedback.list.FeedbackResponseMapper;
import com.efborchardt.productfeedback.application.usecase.feedback.list.ListFeedbackResponseDTO;
import com.efborchardt.productfeedback.domain.feedback.model.Feedback;
import com.efborchardt.productfeedback.domain.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ListFeedbackByProductUseCase {

    private final FeedbackService service;

    @Autowired
    public ListFeedbackByProductUseCase(FeedbackService service) {
        this.service = service;
    }

    public ListFeedbackResponseDTO execute(UUID productId) {
        final List<Feedback> feedbackList = this.service.listByProduct(productId);
        return FeedbackResponseMapper.mapToDtoList(feedbackList);
    }
}
