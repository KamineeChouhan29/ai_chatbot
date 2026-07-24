package com.ai_backend.service;

import com.ai_backend.payload.ChatHistory;
import com.ai_backend.payload.CricketResponse;
import com.ai_backend.repository.ChatHistoryRepository;
// import com.openai.models.images.ImageModel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {
  @Autowired private ChatModel chatModel;
  @Autowired private HuggingApi huggingApi;

  @Autowired private ChatHistoryRepository historyRepository;

  @Autowired private StreamingChatModel streamingChatModel;

  @Autowired private ImageModel imageModel;

  //    public String generateResponse(String inputText) {
  //        String response = chatModel.call(inputText);
  //        return response;
  //    }
  public String generateResponse(String inputText) {

    String response = chatModel.call(inputText);

    ChatHistory history = new ChatHistory();
    history.setQuestion(inputText);
    history.setAnswer(response);
    history.setCreatedAt(LocalDateTime.now());

    historyRepository.save(history);

    return response;
  }

  public Flux<String> streamResponse(String inputText) {
    Flux<String> response = chatModel.stream(inputText);
    return response;
  }

  public CricketResponse generateCricketResponse(String inputText) {
    String promptString = "You are a cricket expert bot. Answer this question: " + inputText;

    ChatResponse cricketResponse = chatModel.call(new Prompt(promptString));

    String responseString = cricketResponse.getResult().getOutput().getText();

    System.out.println(responseString);

    CricketResponse response = new CricketResponse();
    response.setContent(responseString);

    return response;
  }

  public List<String> generateImages(String imageDesc, String size, int numbers)
      throws IOException {
    String template = this.loadPromptTemplate("prompts/image_bot.txt");
    String promptString =
        this.putValuesInPromptTemplate(
            template,
            Map.of("numberOfImages", numbers + "", "description", imageDesc, "size", size));

    ImageResponse imageResponse = imageModel.call(new ImagePrompt(promptString));
    List<String> imageUrls =
        imageResponse.getResults().stream()
            .map(generation -> generation.getOutput().getUrl())
            .collect(Collectors.toList());

    return imageUrls;
  }

  public ResponseEntity<?> processImageRequest(String prompt) {

    try {

      byte[] image = huggingApi.generateImage(prompt);

      return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);

    } catch (Exception e) {

      e.printStackTrace();

      return ResponseEntity.status(500).body("Image generation failed : " + e.getMessage());
    }
  }

  public String loadPromptTemplate(String fileName) throws IOException {
    Path filePath = new ClassPathResource(fileName).getFile().toPath();
    return Files.readString(filePath);
  }

  public String putValuesInPromptTemplate(String template, Map<String, String> variables) {
    for (Map.Entry<String, String> entry : variables.entrySet()) {
      template = template.replace("{" + entry.getKey() + "}", entry.getValue());
    }
    return template;
  }
}
