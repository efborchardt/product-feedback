package com.efborchardt.productfeedback.application.usecase.feedback.list;

import com.efborchardt.productfeedback.domain.feedback.model.Feedback;

import java.util.List;

public class FeedbackResponseMapper {

    private FeedbackResponseMapper() {}

    public static ListFeedbackResponseDTO mapToDtoList(List<Feedback> feedbackList) {
        final List<FeedbackResponseDTO> feedbackResponseDTOList = feedbackList.stream()
                .map(FeedbackResponseMapper::mapToDto)
                .toList();

        return new ListFeedbackResponseDTO(feedbackResponseDTOList);
    }

    public static FeedbackResponseDTO mapToDto(Feedback feedback) {
        return new FeedbackResponseDTO(
                feedback.getId(),
                feedback.getContent(),
                feedback.getSubmissionDate(),
                feedback.getSubmittedBy().getUsername(),
                feedback.getProduct().getName()
        );
    }
}
