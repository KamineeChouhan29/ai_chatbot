package com.ai_backend.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


public class CricketResponse {
   private String content;

   public CricketResponse() {
   }


   public CricketResponse(String content) {
      this.content = content;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }
}
