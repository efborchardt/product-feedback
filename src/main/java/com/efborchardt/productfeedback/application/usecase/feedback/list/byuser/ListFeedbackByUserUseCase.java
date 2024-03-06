package com.efborchardt.productfeedback.application.usecase.feedback.list.byuser;

import com.efborchardt.productfeedback.application.usecase.feedback.list.FeedbackResponseMapper;
import com.efborchardt.productfeedback.application.usecase.feedback.list.ListFeedbackResponseDTO;
import com.efborchardt.productfeedback.domain.feedback.model.Feedback;
import com.efborchardt.productfeedback.domain.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ListFeedbackByUserUseCase {

    private final FeedbackService service;

    @Autowired
    public ListFeedbackByUserUseCase(FeedbackService service) {
        this.service = service;
    }

    public ListFeedbackResponseDTO execute(UUID userId) {
        final List<Feedback> feedbackList = this.service.listByUser(userId);
        return FeedbackResponseMapper.mapToDtoList(feedbackList);
    }
}
