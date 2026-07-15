package com.ai_backend;

import com.ai_backend.payload.CricketResponse;
import com.ai_backend.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
class AiBackendApplicationTests {

	 @Autowired
	 private ChatService chatService;
	@Test
	void contextLoads() throws JsonProcessingException {
		CricketResponse cricketResponse= chatService.generateCricketResponse("who is sachin ?");
		System.out.println(cricketResponse.getContent());

	}

	@Test
	void testTemplate() throws IOException {
		String s=chatService.loadPromptTemplate("prompts/cricket_bot.txt");
		String prompt = chatService.putValuesInPromptTemplate(s, Map.of("inputText", "what is cricket"));

		System.out.println(prompt);
	}

}
