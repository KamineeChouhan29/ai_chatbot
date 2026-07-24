package com.ai_backend.controller;

import com.ai_backend.payload.HelpRequest;
import com.ai_backend.repository.HelpRequestRepository;
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
  private final HelpRequestRepository helpRequestRepository;

  public HelpController(EmailService emailService, HelpRequestRepository helpRequestRepository) {
    this.emailService = emailService;
    this.helpRequestRepository = helpRequestRepository;
  }

  @PostMapping("/contact")
  public ResponseEntity<?> contactSupport(@RequestBody HelpRequest request) {

    // Save to database so we don't lose the request if email fails
    helpRequestRepository.save(request);

    emailService.sendEmail(
        request.getName(), request.getEmail(), request.getSubject(), request.getMessage());

    return ResponseEntity.ok(Map.of("success", true, "message", "Help request sent successfully"));
  }
}
