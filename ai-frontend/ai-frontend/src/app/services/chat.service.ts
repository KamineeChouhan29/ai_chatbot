import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { ChatHistory } from '../model/chatHistory';

@Injectable({
  providedIn: 'root'
})
export class ChatStateService {

  private selectedChat = new BehaviorSubject<ChatHistory | null>(null);

  selectedChat$ = this.selectedChat.asObservable();

  openChat(chat: ChatHistory) {
    this.selectedChat.next(chat);
  }

    private historyRefresh = new Subject<void>();
  historyRefresh$ = this.historyRefresh.asObservable();

  refreshHistory() {
    this.historyRefresh.next();
  }

}