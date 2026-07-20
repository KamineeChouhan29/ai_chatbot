package com.ai_backend.controller;

import com.ai_backend.payload.FeedbackQuestion;
import com.ai_backend.payload.FeedbackRating;
import com.ai_backend.payload.FeedbackRatingRequest;
import com.ai_backend.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")

@CrossOrigin(origins = "https://ai-chatbot-brol.vercel.app")

public class FeedbackController {
    @Autowired
    private FeedbackService service;

    @GetMapping("/questions")
    public List<FeedbackQuestion> getQuestions(){

        return service.getQuestions();

    }

    @PostMapping("/rate")

    public FeedbackRating submitRating(

            @RequestParam Long questionId,

            @RequestParam int rating) {

        return service.saveRating(questionId, rating);

    }
    @PostMapping("/submit")
    public ResponseEntity<String> submitFeedback(
            @RequestBody List<FeedbackRatingRequest> ratings) {

        service.saveAll(ratings);

        return ResponseEntity.ok("Success");
    }
    }
