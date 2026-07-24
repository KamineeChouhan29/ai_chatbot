package com.ai_backend.payload;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback_rating")
public class FeedbackRating {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private FeedbackQuestion question;

  private int rating;

  private LocalDateTime createdAt;

  public FeedbackRating() {}

  public Long getId() {
    return id;
  }

  public FeedbackQuestion getQuestion() {
    return question;
  }

  public void setQuestion(FeedbackQuestion question) {
    this.question = question;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
