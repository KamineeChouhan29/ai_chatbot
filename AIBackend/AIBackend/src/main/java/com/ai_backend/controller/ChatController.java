package com.ai_backend.controller;

import com.ai_backend.payload.ChatHistory;
import com.ai_backend.payload.CricketResponse;
import com.ai_backend.repository.ChatHistoryRepository;
import com.ai_backend.service.ChatService;
import com.ai_backend.service.HuggingApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/chat")

@CrossOrigin(origins = {"https://ai-chatbot-brol.vercel.app", "https://ai-chatbot-system-chi.vercel.app", "http://localhost:4200"})
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private HuggingApi imageService;

    @Autowired
    private ChatHistoryRepository historyRepository;


    @GetMapping("get")
    public ResponseEntity<String> generateResponse(@RequestParam(value = "inputText") String inputText){
        String responseText = chatService.generateResponse(inputText);
        return ResponseEntity.ok(responseText);

    }
    @GetMapping("/stream")
    public Flux<String> streamResponse(@RequestParam(value = "inputText") String inputText){
        Flux<String> response = chatService.streamResponse(inputText);
        return response;

    }
    @GetMapping("/cricketbot")
    public ResponseEntity<CricketResponse> getCricketResponse(@RequestParam("inputText") String inputText)throws JsonProcessingException {
        CricketResponse cricketResponse = chatService.generateCricketResponse(inputText);
        return ResponseEntity.ok(cricketResponse);
        //return ResponseEntity.ok(cricketResponse.getContent());

    }

//    @GetMapping("/image")
//    public List<String> generateImages(@RequestParam("imageDescription") String imageDesc,
//                                       @RequestParam(value = "size", required = false, defaultValue = "500x500") String size,
//                                       @RequestParam(value = "numberOfImages", required = false, defaultValue = "2") int numbers) throws IOException {
//       return chatService.generateImages(imageDesc, size, numbers);
//    }

//    @GetMapping("/image")
//    public ResponseEntity<byte[]> generateImage(
//            @RequestParam String prompt) {
//
//        byte[] image = imageService.generateImage(prompt);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_PNG)
//                .body(image);
//
//
//            return chatService.processImageRequest(prompt);
//
//        }
@GetMapping("/image")
public ResponseEntity<?> generateImage(
        @RequestParam String prompt
){

    return chatService.processImageRequest(prompt);

}




//@GetMapping("/history")
//public List<ChatHistory> getHistory() {
//    return historyRepository.findAll();
//}

    @GetMapping("/history")
    public List<ChatHistory> getHistory() {

        LocalDateTime last48Hours = LocalDateTime.now().minusHours(48);

        return historyRepository.findByCreatedAtAfterOrderByCreatedAtDesc(last48Hours);

    }
}
