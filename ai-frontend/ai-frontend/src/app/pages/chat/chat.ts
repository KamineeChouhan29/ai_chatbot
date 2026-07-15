
import { Component, ChangeDetectorRef, NgZone } from '@angular/core'; // 👈 1. NgZone import kiya
import { SharedModule } from '../../utils/shared.component';
import { Api } from '../../services/api';
import { Message } from '../../model/message';
import { marked } from 'marked';
import { ChatHistory } from '../../model/chatHistory';
import { ChatStateService } from '../../services/chat.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './chat.html',
  styleUrl: './chat.css',
})
export class Chat {
  // 👈 2. Constructor me NgZone ko inject kiya
  constructor(
    private apiService: Api, 
    private cdr: ChangeDetectorRef,
    private zone: NgZone,
    private chatState : ChatStateService
  ) {}
  
  inputTextPrompt = "";
  messagesArray: Message[] = [];
  history:ChatHistory[]=[];
  loadingChat = false;



ngOnInit(): void {

  this.loadHistory();

  this.chatState.selectedChat$.subscribe(chat => {

    if (chat) {

      this.messagesArray = [];

      this.messagesArray.push(
        new Message(chat.question, true)
      );

      this.messagesArray.push(
        new Message(chat.answer, false)
      );

    }

  });

}

  loadHistory() {
    this.apiService.getHistory().subscribe({
      next: (data) => {
        this.history = data;
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  askButtonClicked() {

    if (this.inputTextPrompt.trim() === '') {
      return;
    }

    this.loadingChat = true;

    this.messagesArray.push(new Message(this.inputTextPrompt, true));

    const userPrompt = this.inputTextPrompt;

    this.inputTextPrompt = '';

    this.apiService.getRandomeResponse(userPrompt).subscribe({

      // next: (response: any) => {

      //   const aiMessageText =
      //     typeof response === 'string'
      //       ? response
      //       : (response.reply || response.text || JSON.stringify(response));

      //   this.zone.run(() => {

      //     this.messagesArray.push(new Message(aiMessageText, false));

      //     this.loadingChat = false;

      //     // History refresh
      //     this.loadHistory();

      //     this.cdr.detectChanges();

      //   });

      // },
      next: (response: string) => {

  this.messagesArray = [
    ...this.messagesArray,
    new Message(response, false)
  ];

  this.loadingChat = false;

  this.loadHistory();

  this.cdr.markForCheck();

},

      error: (error) => {

        console.log(error);

        this.zone.run(() => {

          this.loadingChat = false;

          this.cdr.detectChanges();

        });

      }

    });

  }

 formatMessage(text: string | undefined): string {
  return marked.parse(text ?? '') as string;
}

}