import { Component, OnInit } from '@angular/core';
import { QuizService } from '../service/quiz.service';
import { CommonModule } from '@angular/common';
import { QuizResultBannerComponent } from './quiz-result-banner.component';
import { QuizManagerHeaderComponent } from './quiz-manager-header.component';
import { QuizFormComponent } from './quiz-form.component';
import { QuestionDTO } from '../types/question.dto';
import { QuizResult, ScoreSummary } from '../types/core';

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
      <app-quiz-manager-header class="justify-between" topic="AWS" />
      <app-quiz-form 
          [questions]="_questions" 
          (submitHandler)="handleSubmit($event)"
          (retakeHandler)="handleRetake()"
      />
      <!--END: QUESTION MANAGER-->
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

  _questions!: QuestionDTO[];
  _scoreSummary!: ScoreSummary;

  constructor(
    private quizService: QuizService,
  ) { }

  ngOnInit(): void {
    this._questions = this.quizService.generateQuiz('AWS');
  }


  handleSubmit(quizResult: QuizResult) {
    this.willShowResult = true
    this._scoreSummary = quizResult.score
  }

  handleRetake() {
    // this is to refresh the page?
    this._questions = this._questions;
  }

}
