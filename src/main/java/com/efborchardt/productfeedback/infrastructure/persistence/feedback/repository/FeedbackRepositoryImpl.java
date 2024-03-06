package com.efborchardt.productfeedback.infrastructure.persistence.feedback.repository;

import com.efborchardt.productfeedback.domain.feedback.model.Feedback;
import com.efborchardt.productfeedback.domain.feedback.repository.FeedbackRepository;
import com.efborchardt.productfeedback.infrastructure.persistence.feedback.mapper.FeedbackMapper;
import com.efborchardt.productfeedback.infrastructure.persistence.feedback.model.FeedbackEntity;
import com.efborchardt.productfeedback.infrastructure.persistence.feedback.model.SpringDataFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository {

    private final SpringDataFeedbackRepository springDataFeedbackRepository;

    @Autowired
    public FeedbackRepositoryImpl(SpringDataFeedbackRepository springDataFeedbackRepository) {
        this.springDataFeedbackRepository = springDataFeedbackRepository;
    }

    @Override
    public void save(Feedback entity) {
        this.springDataFeedbackRepository.save(FeedbackMapper.mapToPersistence(entity));
    }

    @Override
    public Optional<Feedback> findById(UUID id) {
        final Optional<FeedbackEntity> feedbackEntity = this.springDataFeedbackRepository.findById(id);
        return feedbackEntity.map((FeedbackMapper::mapToDomain));
    }

    @Override
    public List<Feedback> findByProductId(UUID id) {
        final List<FeedbackEntity> feedbackEntityList = this.springDataFeedbackRepository.findByProductId(id);
        return FeedbackMapper.mapListToDomain(feedbackEntityList);
    }

    @Override
    public List<Feedback> findByUserId(UUID id) {
        final List<FeedbackEntity> feedbackEntityList = this.springDataFeedbackRepository.findByUserId(id);
        return FeedbackMapper.mapListToDomain(feedbackEntityList);
    }
}
