import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { QuizComponent } from './quiz/components/quiz.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule, 
    RouterOutlet,
    QuizComponent,
  ],
  templateUrl: './app.component.html',
})
export class AppComponent {
  title = 'angular-client';
}
