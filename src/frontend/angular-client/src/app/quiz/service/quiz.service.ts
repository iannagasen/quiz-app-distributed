import { Injectable } from '@angular/core';
import * as quizJson from '../../../../../../../payloads/quiz.json'
import questionJson from '../../../../../../../payloads/questions.json';
import { QuestionDTO } from '../types/question.dto';
import { Quiz } from '../types/core';
import { HttpClient } from '@angular/common/http';
import { environment } from './../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QuizService {

  constructor(
    private _http: HttpClient,
  ) { }

  getQuiz(quizId: number): Quiz {
    /**
     * return a dummy data for now
     */
    return quizJson;
  }

  generateQuiz(topic: string): Observable<QuestionDTO[]> {
    /**
     * return a dummy data for now
        return questionJson;
     */
    return this._http.get<QuestionDTO[]>(`${environment.backendApi}/question?topic=${topic}`)
  }
}
