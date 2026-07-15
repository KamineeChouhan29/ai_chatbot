package com.ai_backend.service;

import com.ai_backend.payload.FeedbackQuestion;
import com.ai_backend.payload.FeedbackRating;
import com.ai_backend.payload.FeedbackRatingRequest;
import com.ai_backend.repository.FeedbackQuestionRepository;
import com.ai_backend.repository.FeedbackRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackQuestionRepository questionRepository;

    @Autowired
    private FeedbackRatingRepository ratingRepository;

    public List<FeedbackQuestion> getQuestions(){

        return questionRepository.findAll();

    }

    public FeedbackRating saveRating(Long questionId, int rating){

        FeedbackQuestion question =
                questionRepository.findById(questionId).orElseThrow();

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
