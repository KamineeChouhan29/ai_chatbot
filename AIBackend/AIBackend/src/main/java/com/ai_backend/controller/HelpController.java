package com.ai_backend.controller;

import com.ai_backend.payload.HelpRequest;
import com.ai_backend.service.EmailService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/help")
@CrossOrigin(
    origins = {
      "https://ai-chatbot-brol.vercel.app",
      "https://ai-chatbot-system-chi.vercel.app",
      "http://localhost:4200"
    })
public class HelpController {

  private final EmailService emailService;

  public HelpController(EmailService emailService) {
    this.emailService = emailService;
  }

  @PostMapping("/contact")
  public ResponseEntity<?> contactSupport(@RequestBody HelpRequest request) {

    emailService.sendEmail(
        request.getName(), request.getEmail(), request.getSubject(), request.getMessage());

    return ResponseEntity.ok(Map.of("success", true, "message", "Help request sent successfully"));
  }
}
