import { Component, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { Sidemenu } from './components/sidemenu/sidemenu';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone : true,
  imports: [RouterOutlet,CommonModule,Sidemenu],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  isMobileMenuOpen = false;

  constructor(public router: Router) {}
  title = 'ai-frontend';

  toggleMobileMenu() {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }
}
