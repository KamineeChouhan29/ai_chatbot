import { Component, ChangeDetectorRef, NgZone } from '@angular/core';
import { SharedModule } from '../../utils/shared.component';
import { Api } from '../../services/api';
import { Message } from '../../model/message';

@Component({
  selector: 'app-cricket',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './cricket.html',
  styleUrl: './cricket.css',
})
export class Cricket {

  constructor(
    private apiService: Api,
    private cdr: ChangeDetectorRef,
    private zone: NgZone
  ) {}

  inputTextPrompt = '';
  messagesArray: Message[] = [];
  loadingChat = false;

  askButtonClicked() {

    if (this.inputTextPrompt.trim() === '') {
      return;
    }

    this.loadingChat = true;

    const userPrompt = this.inputTextPrompt;

    // User message
    this.messagesArray = [
      ...this.messagesArray,
      new Message(userPrompt, true)
    ];

    this.inputTextPrompt = '';

    console.log("User Prompt:", userPrompt);

    this.apiService.getCricketResponse(userPrompt).subscribe({

      next: (response) => {

        console.log("Full Response:", response);
        console.log("Content:", response.content);

        this.zone.run(() => {

          this.messagesArray = [
            ...this.messagesArray,
            new Message(response.content, false)
          ];

          this.loadingChat = false;

          console.log("Messages Array:", this.messagesArray);

          this.cdr.detectChanges();
        });
      },

      error: (err) => {

        console.error(err);

        this.zone.run(() => {
          this.loadingChat = false;
          this.cdr.detectChanges();
        });

      },

      complete: () => {
        console.log("Request Completed");
      }

    });
  }
}
