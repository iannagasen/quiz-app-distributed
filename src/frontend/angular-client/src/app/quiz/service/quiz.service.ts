import { Injectable } from '@angular/core';
import * as quizJson from '../../../../../../../payloads/quiz.json'
import questionJson from '../../../../../../../payloads/questions.json';
import { Quiz } from '../types/quiz';
import { QuestionDTO } from '../types/question.dto';

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

  generateQuiz(topic: string): QuestionDTO[] {
    /**
     * return a dummy data for now
     */
    return questionJson;
  }
}
