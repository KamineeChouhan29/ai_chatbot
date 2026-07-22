package com.ai_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HuggingApi {
    @Value("${huggingface.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

//     public byte[] generateImage(String prompt) {

//         HttpHeaders headers = new HttpHeaders();
//         headers.setBearerAuth(apiKey);
//         headers.setContentType(MediaType.APPLICATION_JSON);
//         headers.setAccept(List.of(MediaType.IMAGE_PNG));

//         String body = """
//                 {
//                   "inputs":"%s"
//                 }
//                 """.formatted(prompt);

//         HttpEntity<String> request =
//                 new HttpEntity<>(body, headers);

//         ResponseEntity<byte[]> response =
//                 restTemplate.exchange(
//                         "https://router.huggingface.co/hf-inference/models/black-forest-labs/FLUX.1-schnell",
//                         HttpMethod.POST,
//                         request,
//                         byte[].class
//                 );

//         return response.getBody();


    //}

    public byte[] generateImage(String prompt) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.IMAGE_PNG, MediaType.IMAGE_JPEG));

            java.util.Map<String, String> body = java.util.Map.of("inputs", prompt);
            HttpEntity<java.util.Map<String, String>> request = new HttpEntity<>(body, headers);

            // Using the official Hugging Face API endpoint for FLUX
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    "https://api-inference.huggingface.co/models/black-forest-labs/FLUX.1-schnell",
                    HttpMethod.POST,
                    request,
                    byte[].class
            );

            return response.getBody();

        } catch (org.springframework.web.client.HttpStatusCodeException e) {
            String responseBody = e.getResponseBodyAsString();
            e.printStackTrace();
            System.err.println("Hugging Face API Error Body: " + responseBody);
            throw new RuntimeException("Error from Hugging Face API (" + e.getStatusCode() + "): " + responseBody, e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating image: " + e.getMessage(), e);
        }
    }
}