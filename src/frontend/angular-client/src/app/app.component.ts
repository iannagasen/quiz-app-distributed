import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { QuizManagerComponent } from './quiz/components/quiz-manager.component';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule, 
    RouterOutlet,
  ],
  template: `
    <router-outlet></router-outlet>
  `,
})
export class AppComponent { 

}
