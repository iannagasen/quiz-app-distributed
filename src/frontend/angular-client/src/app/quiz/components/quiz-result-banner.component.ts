import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ScoreSummary } from '../types/core';

@Component({
  selector: 'app-quiz-result-banner',
  standalone: true,
  imports: [
    CommonModule
  ],
  template: `
    <div *ngIf="willShowResult"
      class="fixed inset-0 z-[999] grid h-screen w-screen place-items-center backdrop-blur-sm transition-opacity duration-300">
      <div class="rounded-md bg-clr-modal opacity-85 my-auto">
        <div class='text-3xl font-extrabold mb-2'>Quiz Statistics</div>
        <div class='text-xl'>Score: {{scoreSummary.score}}</div>
        <div class='text-xl'>Unanswered {{scoreSummary.unanswered}}</div>
        <div class='text-xl'>Total: {{scoreSummary.total}}</div>
        <button (click)="onButtonClick()" class="bg-clr-accent rounded-md w-full">
          OK
        </button>
    </div>
  </div>  
`
})
export class QuizResultBannerComponent {

  @Input({ alias: "show", required: true }) 
  willShowResult: boolean = false;

  @Input({ alias: "score" }) 
  scoreSummary!: ScoreSummary;

  @Output() 
  buttonClicked = new EventEmitter<boolean>(); 

  onButtonClick() {
    this.buttonClicked.emit(true);
  }
}
