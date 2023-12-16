import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { QuizManagerComponent } from './quiz/components/quiz-manager.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    QuizManagerComponent
  ],
  template: `
    <div class='flex min-h-screen items-stretch'>
      <div class='basis-1/4 m-2'>
        navBar
      </div>
      <div class='basis-1/2 m-2'>
        <app-quiz-manager></app-quiz-manager>
      </div>
      <div class='basis-1/4 m-2'>
        statBar
      </div>
    </div>
  `,
})
export class DashboardComponent {
  
}
