package com.ai_backend.payload;

public class FeedbackRatingRequest {
  private Long questionId;
  private int rating;

  public FeedbackRatingRequest() {}

  public Long getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Long questionId) {
    this.questionId = questionId;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }
}
