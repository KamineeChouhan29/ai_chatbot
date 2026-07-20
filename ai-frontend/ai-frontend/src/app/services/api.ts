import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CricketResponse } from '../model/cricket.response';
import { ChatHistory } from '../model/chatHistory';
import { FeedbackQuestion } from '../model/feedback';

@Injectable({
  providedIn: 'root',
})
export class Api {
  constructor(private http : HttpClient
  ) {}

  baseUrl= 'https://ai-chatbot-backend-yds3.onrender.com/api/chat';
  feedbackBaseUrl = "https://ai-chatbot-backend-yds3.onrender.com/api/feedback";
  getRandomeResponse(prompt : string):Observable<string>{
    return this.http.get(`${this.baseUrl}/get?inputText=${prompt}`,{
     responseType : 'text',
    });
  }

  // getCricketResponse(cricketPrompt: string) : Observable<string>{
  //     return this.http.get(`${this.baseUrl}/cricketbot?inputText=${cricketPrompt}`,{
  //       responseType : 'text'
  //     });
  // }
  getCricketResponse(cricketPrompt: string): Observable<CricketResponse> {
  return this.http.get<CricketResponse>(
    `${this.baseUrl}/cricketbot?inputText=${cricketPrompt}`
  );
}

  // getImagesResponse(imageDescription : string) : Observable<string[]> {
  //    return this.http.get<string[]>(`${this.baseUrl}/image?prompt=${imageDescription}`);
  // }
  // getImagesResponse(prompt: string): Observable<Blob> {
  // return this.http.get(
  //   `${this.baseUrl}/image?prompt=${encodeURIComponent(prompt)}`,
  //   {
  //     responseType: 'blob'
  //   }
  // );
  // }
  getImagesResponse(prompt:string): Observable<HttpResponse<Blob>> {


    return this.http.get(
      `${this.baseUrl}/image?prompt=${encodeURIComponent(prompt)}`,
      {
        observe: 'response',
        responseType: 'blob'
      }
    );

  }


  getHistory(): Observable<ChatHistory[]> {
  return this.http.get<ChatHistory[]>(
    `${this.baseUrl}/history`
  );
}

getFeedbackQuestions() {
  return this.http.get<FeedbackQuestion[]>(
    this.feedbackBaseUrl + "/questions"
  );
}

submitRating(questionId: number, rating: number) {
  return this.http.post(
    this.feedbackBaseUrl +
      "/rate?questionId=" +
      questionId +
      "&rating=" +
      rating,
    {}
  );
}

submitFeedback(feedback: any[]) {

  return this.http.post(
    this.feedbackBaseUrl + "/submit",
    feedback
  );

}
sendHelpRequest(data: any) {

  return this.http.post(
    "https://ai-chatbot-backend-yds3.onrender.com/api/help/contact",
    data,
    {
      responseType: 'text'
    }
  );

}
}
