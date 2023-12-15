import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { QuizManagerComponent } from './quiz/components/quiz-manager.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule, 
    RouterOutlet,
    QuizManagerComponent
  ],
  templateUrl: './app.component.html',
})
export class AppComponent {
  title = 'angular-client';
}
