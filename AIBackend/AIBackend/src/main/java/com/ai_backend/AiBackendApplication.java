package com.ai_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AiBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(AiBackendApplication.class, args);
  }
}
