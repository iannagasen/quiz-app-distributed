import { Injectable } from '@angular/core';
import * as quizJson from '../../../../../../../payloads/quiz.json'
import { Quiz } from '../types/quiz';

@Injectable({
  providedIn: 'root'
})
export class QuizService {

  constructor() { }

  getQuiz(quizId: number): Quiz {
    /**
     * return a dummy data for now
     */
    return quizJson;
  }
}
