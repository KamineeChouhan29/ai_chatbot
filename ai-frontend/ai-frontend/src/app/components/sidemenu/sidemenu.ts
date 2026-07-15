import { Component, OnInit } from '@angular/core';
import { SharedModule } from '../../utils/shared.component';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

import { error } from 'console';
import { ChatHistory } from '../../model/chatHistory';
import { Api } from '../../services/api';
import { ChatStateService } from '../../services/chat.service';

@Component({
  selector: 'app-sidemenu',
  standalone: true,
  imports: [SharedModule,RouterLinkActive,RouterLink],
  templateUrl: './sidemenu.html',
  styleUrl: './sidemenu.css',
})
export class Sidemenu implements OnInit{
 history: ChatHistory[] = [];
   showHistory: boolean = false;


  constructor(
    private api: Api,
    private chatState: ChatStateService,
    private router : Router
  ) {}

  ngOnInit(): void {
    this.loadHistory();
  }

  loadHistory() {
  this.api.getHistory().subscribe({
    next: (data: ChatHistory[]) => {
      this.history = data;
    },
    error: (err) => {
      console.error(err);
    }
  });
}

  toggleHistory() {
    this.showHistory = !this.showHistory;
  }

  openHistory(item: ChatHistory) {

  this.chatState.openChat(item);

  this.router.navigate(['/chat']);

}
}
