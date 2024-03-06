package com.efborchardt.productfeedback.application.usecase.feedback.list;

import java.util.List;

public class ListFeedbackResponseDTO {

    private List<FeedbackResponseDTO> feedbacks;

    public ListFeedbackResponseDTO(List<FeedbackResponseDTO> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<FeedbackResponseDTO> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackResponseDTO> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
