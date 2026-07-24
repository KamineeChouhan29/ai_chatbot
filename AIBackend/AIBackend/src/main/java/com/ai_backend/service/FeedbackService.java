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
    if (questionRepository.count() == 0) {
      questionRepository.save(new FeedbackQuestion("How satisfied are you with the AI's answers?"));
      questionRepository.save(
          new FeedbackQuestion("How would you rate the speed of the AI responses?"));
      questionRepository.save(
          new FeedbackQuestion("How satisfied are you with the Image Generator?"));
      questionRepository.save(new FeedbackQuestion("How satisfied are you with the Cricket Bot?"));
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
