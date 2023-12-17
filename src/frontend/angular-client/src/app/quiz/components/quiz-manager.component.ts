import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { QuizService } from '../service/quiz.service';
import { CommonModule } from '@angular/common';
import { QuizResultBannerComponent } from './quiz-result-banner.component';
import { QuizManagerHeaderComponent } from './quiz-manager-header.component';
import { QuizFormComponent } from './quiz-form.component';
import { QuestionDTO } from '../types/question.dto';
import { QuizResult, ScoreSummary } from '../types/core';

import { Observable, interval, Subscription } from 'rxjs';
import { take, tap } from 'rxjs/operators';

@Component({
  selector: 'app-quiz-manager',
  standalone: true,
  imports: [
    CommonModule,
    QuizResultBannerComponent,
    QuizManagerHeaderComponent,
    QuizFormComponent
  ],
  template: `
    <div class="flex flex-col p-2 m-2">
      <app-quiz-manager-header class="justify-between" [topic]="topic" />
      <app-quiz-form 
          *ngIf="questions.length !== 0"
          [questions]="questions" 
          (submitHandler)="handleSubmit($event)"
          (retakeHandler)="handleRetake()"
      />
      <app-quiz-result-banner 
          [show]="willShowResult" 
          (buttonClicked)="this.willShowResult = false" 
          [score]="_scoreSummary"
      />
    </div>

  `, 
})
export class QuizManagerComponent implements OnInit {

  willShowResult = false;
  topic: string = 'AWS'

  questions: QuestionDTO[] = [];
  _scoreSummary!: ScoreSummary;

  topicSubscription?: Subscription;

  constructor(
    private quizService: QuizService,
    private _changeDetectorRef: ChangeDetectorRef
  ) { }
  
  ngOnInit(): void {
    this.initializeQuestions()
  }

  handleSubmit(quizResult: QuizResult) {
    this.willShowResult = true
    this._scoreSummary = quizResult.score
  }

  handleRetake() {
    this.questions = this.questions;
  }

  private initializeQuestions() {
    // TODO:  observables not detecting changes when data arrive
    this.quizService.generateQuiz('AWS')
      .pipe(
        take(1)
      )
      .subscribe({
        next: data => {
          console.log("DATA")
          console.log(data)
          this.questions = data
          console.log("QUESTIONS")
          console.log(this.questions)
        },
        error: err => console.error("error fetching data", err)
      })
  }

}
