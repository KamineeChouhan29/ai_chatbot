package com.ai_backend.repository;

import com.ai_backend.payload.ChatHistory;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
  List<ChatHistory> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime time);
}
