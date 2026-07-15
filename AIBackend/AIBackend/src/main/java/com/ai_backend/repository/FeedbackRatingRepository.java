package com.ai_backend.repository;

import com.ai_backend.payload.FeedbackRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRatingRepository extends JpaRepository<FeedbackRating, Long> {
}
