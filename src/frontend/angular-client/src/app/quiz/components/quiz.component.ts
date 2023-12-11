import { Component, OnInit } from '@angular/core';
import { QuizService } from '../service/quiz.service';
import { Quiz } from '../types/quiz';
import { CommonModule } from '@angular/common';
import { QuestionDTO } from '../types/question.dto';
import { QuestionState } from '../types/question.state';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-quiz',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './quiz.component.html', 
})
export class QuizComponent implements OnInit {

  questionsState: QuestionState[] = []
  isSubmitted = false;
  topic: string = 'AWS'
  scoreSummary: {
    score: number,
    unanswered: number,
    total: number
  } | undefined = undefined;

  constructor(
    private quizService: QuizService,
  ) { }


  ngOnInit(): void {
    this.questionsState = this.quizService.generateQuiz('AWS');
  }


  getRows(text: string): number {
    const length = text.length
    if (length <= 90) return 1;
    if (length <= 180) return 2;
    return 3;
  }


  onSubmit(form: NgForm) {
    /**
     * 1. show the banner with calculated score
     * 2. TODO: call the api 
     */
    // console.log(object)
    // console.log(form)
    this.isSubmitted = true

    this.scoreSummary = this.questionsState.reduce(
      (result, question) => {
        const selectedChoice = question.choices.find(c => c.id === question.selectedChoiceId);

        if (selectedChoice?.correct) result.score ++;
        if (!selectedChoice) result.unanswered ++;

        return result;
      },
      { score: 0, unanswered: 0, total: this.questionsState.length}
    )
  }
}
