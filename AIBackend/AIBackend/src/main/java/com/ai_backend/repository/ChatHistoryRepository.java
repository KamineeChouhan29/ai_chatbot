package com.ai_backend.repository;

import com.ai_backend.payload.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
    List<ChatHistory> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime time);
}
