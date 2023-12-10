import { Component, OnInit } from '@angular/core';
import { QuizService } from '../service/quiz.service';
import { Quiz } from '../types/quiz';
import { CommonModule } from '@angular/common';
import { QuestionDTO } from '../types/question.dto';
import { QuestionState } from '../types/question.state';

@Component({
  selector: 'app-quiz',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './quiz.component.html', 
})
export class QuizComponent implements OnInit {


  questionsState: QuestionState[] = []

  topic: string = 'AWS'

  constructor(
    private quizService: QuizService,
  ) { }

  ngOnInit(): void {
    this.questionsState = this.quizService.generateQuiz('AWS');
  }

  getRows(text: string): number {
    const length = text.length
    console.log(`length is = ${length}`)
    if (length <= 90) return 1;
    if (length <= 180) return 2;
    return 3;
  }


  isSelected(questionId: number, choiceId: number) {
    const id = this.questionsState.find(q => q.id === questionId)?.selectedChoiceId
    return id === choiceId;
  }
  

  selectAnswer(questionId: number, choiceId: number): void {
    const question = this.questionsState.find(q => q.id === questionId);

    if (question) {
      question.selectedChoiceId = choiceId;
    }
  }

}
