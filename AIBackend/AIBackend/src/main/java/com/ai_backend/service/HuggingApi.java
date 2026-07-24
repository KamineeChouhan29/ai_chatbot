package com.ai_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class HuggingApi {

  @Value("${huggingface.api.key}")
  private String apiKey;

  @Autowired private RestTemplate restTemplate;

  private static final String API_URL =
      "https://router.huggingface.co/fal-ai/models/black-forest-labs/FLUX.1-schnell";

  public byte[] generateImage(String prompt) {

    System.out.println("=================================");
    System.out.println("IMAGE REQUEST (Using Pollinations.ai API)");
    System.out.println("Prompt : " + prompt);
    System.out.println("=================================");

    try {

      // Hugging Face has completely removed free text-to-image generation
      // for these models (they return 410 Gone or 400 Bad Request).
      // To provide a permanent, free fix, we use Pollinations.ai which requires no API key.
      String encodedPrompt =
          java.net.URLEncoder.encode(prompt, java.nio.charset.StandardCharsets.UTF_8);
      String url = "https://image.pollinations.ai/prompt/" + encodedPrompt;

      ResponseEntity<byte[]> response =
          restTemplate.exchange(url, HttpMethod.GET, null, byte[].class);

      System.out.println("Status : " + response.getStatusCode());

      if (response.getBody() == null) {
        throw new RuntimeException("Pollinations returned empty image");
      }

      System.out.println("Image Size : " + response.getBody().length + " bytes");

      return response.getBody();

    } catch (HttpStatusCodeException e) {
      System.out.println("HTTP ERROR");
      System.out.println("Status : " + e.getStatusCode());
      System.out.println("Body : " + e.getResponseBodyAsString());

      throw new RuntimeException("API Error : " + e.getResponseBodyAsString());
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Image generation failed : " + e.getMessage());
    }
  }
}
