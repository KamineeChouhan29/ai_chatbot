package com.ai_backend.controller;

import com.ai_backend.payload.HelpRequest;
import com.ai_backend.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
    @RequestMapping("/api/help")
    @CrossOrigin(origins="http://localhost:4200")
    public class HelpController {


        private final EmailService emailService;


        public HelpController(EmailService emailService){
            this.emailService = emailService;
        }


    @PostMapping("/contact")
    public ResponseEntity<?> contactSupport(@RequestBody HelpRequest request) {

        emailService.sendEmail(
                request.getName(),
                request.getEmail(),
                request.getSubject(),
                request.getMessage()
        );

        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Help request sent successfully"
                )
        );
    }


        }


