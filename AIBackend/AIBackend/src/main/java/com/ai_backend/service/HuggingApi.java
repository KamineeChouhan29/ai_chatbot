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

        String body = """
        {
          "inputs":"%s"
        }
        """.formatted(prompt);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                "https://router.huggingface.co/hf-inference/models/black-forest-labs/FLUX.1-schnell",
                HttpMethod.POST,
                request,
                byte[].class
        );

        return response.getBody();

    } catch (Exception e) {
        e.printStackTrace();   // <-- Isse actual error logs me dikhega
        throw e;
    }
}
}