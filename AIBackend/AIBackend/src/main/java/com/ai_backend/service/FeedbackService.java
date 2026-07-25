package com.ai_backend.service;

import com.ai_backend.payload.FeedbackQuestion;
import com.ai_backend.payload.FeedbackRating;
import com.ai_backend.payload.FeedbackRatingRequest;
import com.ai_backend.repository.FeedbackQuestionRepository;
import com.ai_backend.repository.FeedbackRatingRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
  @Autowired private FeedbackQuestionRepository questionRepository;

  @Autowired private FeedbackRatingRepository ratingRepository;

  @jakarta.annotation.PostConstruct
  public void initQuestions() {
    // If we don't have exactly 10 questions, reset the database and add them
    if (questionRepository.count() != 10) {
      // Delete existing ratings first to avoid foreign key constraint errors
      ratingRepository.deleteAll();
      questionRepository.deleteAll();

      questionRepository.save(new FeedbackQuestion("How satisfied are you with Ask AI?"));
      questionRepository.save(new FeedbackQuestion("How accurate were AI answers?"));
      questionRepository.save(new FeedbackQuestion("Was AI response fast enough?"));
      questionRepository.save(new FeedbackQuestion("How useful is Cricket Bot?"));
      questionRepository.save(new FeedbackQuestion("How satisfied are you with Image Generator?"));
      questionRepository.save(new FeedbackQuestion("How attractive is the UI?"));
      questionRepository.save(new FeedbackQuestion("Was navigation easy?"));
      questionRepository.save(new FeedbackQuestion("Would you recommend this AI Assistant?"));
      questionRepository.save(new FeedbackQuestion("Overall experience with this application?"));
      questionRepository.save(new FeedbackQuestion("Rate this AI Assistant project."));
    }
  }

  public List<FeedbackQuestion> getQuestions() {
    return questionRepository.findAll();
  }

  public FeedbackRating saveRating(Long questionId, int rating) {

    FeedbackQuestion question = questionRepository.findById(questionId).orElseThrow();

    FeedbackRating feedback = new FeedbackRating();

    feedback.setQuestion(question);
    feedback.setRating(rating);
    feedback.setCreatedAt(LocalDateTime.now());

    return ratingRepository.save(feedback);
  }

  public void saveAll(List<FeedbackRatingRequest> ratings) {

    for (FeedbackRatingRequest r : ratings) {

      saveRating(r.getQuestionId(), r.getRating());
    }
  }
}
