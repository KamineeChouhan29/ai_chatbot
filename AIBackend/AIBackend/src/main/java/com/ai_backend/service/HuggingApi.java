package com.ai_backend.service;

import java.util.Map;
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
    System.out.println("IMAGE REQUEST - HUGGING FACE");
    System.out.println("Prompt: " + prompt);
    System.out.println("=================================");

    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setBearerAuth(apiKey);

      Map<String, String> body = Map.of("inputs", prompt);
      HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

      ResponseEntity<byte[]> response =
          restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, byte[].class);

      System.out.println("Status: " + response.getStatusCode());
      System.out.println("Content-Type: " + response.getHeaders().getContentType());

      if (response.getBody() == null) {
        throw new RuntimeException("Hugging Face returned empty image body");
      }

      System.out.println("Image Size: " + response.getBody().length + " bytes");

      return response.getBody();

    } catch (HttpStatusCodeException e) {
      System.out.println("HUGGING FACE HTTP ERROR");
      System.out.println("Status: " + e.getStatusCode());
      System.out.println("Response: " + e.getResponseBodyAsString());

      throw new RuntimeException("Hugging Face API Error: " + e.getResponseBodyAsString());
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Image generation failed : " + e.getMessage());
    }
  }
}
