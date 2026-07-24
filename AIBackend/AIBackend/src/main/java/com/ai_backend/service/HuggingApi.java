package com.ai_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class HuggingApi {

    @Value("${huggingface.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL =
            "https://router.huggingface.co/fal-ai/models/black-forest-labs/FLUX.1-schnell";

    public byte[] generateImage(String prompt) {

        System.out.println("======================================");
        System.out.println("HUGGING FACE IMAGE REQUEST START");
        System.out.println("Prompt : " + prompt);
        System.out.println("API URL : " + API_URL);
        System.out.println("API Key Present : " + (apiKey != null));
        System.out.println("API Key Length : " + (apiKey == null ? 0 : apiKey.length()));
        System.out.println("======================================");

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.IMAGE_PNG));

            Map<String, String> body = Map.of(
                    "inputs", prompt
            );

            HttpEntity<Map<String, String>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<byte[]> response =
                    restTemplate.exchange(
                            API_URL,
                            HttpMethod.POST,
                            request,
                            byte[].class
                    );

            System.out.println("Status Code : " + response.getStatusCode());
            System.out.println("Response Size : "
                    + (response.getBody() == null ? 0 : response.getBody().length));

            return response.getBody();

        }

        catch (HttpStatusCodeException e) {

            System.out.println("HTTP ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Body : " + e.getResponseBodyAsString());

            throw new RuntimeException(
                    "HuggingFace Error : " + e.getStatusCode()
                            + "\n"
                            + e.getResponseBodyAsString(),
                    e
            );
        }

        catch (Exception e) {

            System.out.println("GENERAL ERROR");
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }
}