package com.efborchardt.productfeedback.infrastructure.persistence.feedback.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataFeedbackRepository extends JpaRepository<FeedbackEntity, UUID> {

    @Query("SELECT f FROM FeedbackEntity f WHERE f.product.id = :id")
    List<FeedbackEntity> findByProductId(@Param("id") UUID id);

    @Query("SELECT f FROM FeedbackEntity f WHERE f.submittedBy.id = :id")
    List<FeedbackEntity> findByUserId(@Param("id") UUID id);
}
