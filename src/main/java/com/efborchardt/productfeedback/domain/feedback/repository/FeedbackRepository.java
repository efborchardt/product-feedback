package com.efborchardt.productfeedback.domain.feedback.repository;

import com.efborchardt.productfeedback.domain._shared.repository.DefaultRepository;
import com.efborchardt.productfeedback.domain.feedback.model.Feedback;

import java.util.List;
import java.util.UUID;

public interface FeedbackRepository extends DefaultRepository<Feedback> {

    List<Feedback> findByProductId(UUID id);
    List<Feedback> findByUserId(UUID id);
}
