import { Component, OnInit } from '@angular/core';
import { QuizService } from '../service/quiz.service';
import { Quiz } from '../types/quiz';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-quiz',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './quiz.component.html',
})
export class QuizComponent implements OnInit {

  quiz: Quiz | undefined = undefined;
  topic: string = 'AWS'

  constructor(
    private quizService: QuizService,
  ) { }

  ngOnInit(): void {
    this.quiz = this.quizService.getQuiz(0);
  }

  getRows(text: string): number {
    const length = text.length
    console.log(`length is = ${length}`)
    if (length <= 80) return 1;
    if (length <= 180) return 2;
    return 3;
  }
}
