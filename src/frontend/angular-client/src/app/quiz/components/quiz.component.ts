import { Component, OnInit } from '@angular/core';
import { QuizService } from '../service/quiz.service';
import { CommonModule } from '@angular/common';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, NgForm, ReactiveFormsModule } from '@angular/forms';
import { QuestionDTO } from '../types/question.dto';

@Component({
  selector: 'app-quiz',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './quiz.component.html', 
})
export class QuizComponent implements OnInit {
  /**
   * WORKFLOW
   * submit
   *  - retake(refresh the form when retaking) - submit
   */

  isSubmitted = false;
  willShowResult = false;
  topic: string = 'AWS'
  scoreSummary!: { score: number, unanswered: number, total: number };

  quizForm!: FormGroup;

  private _questions!: QuestionDTO[];


  constructor(
    private quizService: QuizService,
    private formBuilder: FormBuilder,
  ) { }

  ngOnInit(): void {
    const _questions = this.quizService.generateQuiz('AWS');

    const questionsFormGroup = _questions.map(question => (
      this.formBuilder.group({
        question: new FormControl(question.question),
        selectedChoiceId: -1,
        correctChoiceId: question.choices.find(c => c.correct)?.id,
        choices: this.formBuilder.array(
          question.choices.map(choice => (
            this.formBuilder.group({
              id: choice.id,
              value: choice.value,
              explanation: choice.explanation,
              correct: choice.correct,
              initial: [null]
            })
          ))
        )
      })
    ))

    // initialize form using the reactive approach
    this.quizForm = this.formBuilder.group({
      questions: this.formBuilder.array(questionsFormGroup)
    })
  }


  getRows(text: string): number {
    const length = text.length
    if (length <= 90) return 1;
    if (length <= 180) return 2;
    return 3;
  }

  onClickFormButton() {
    if ( !this.isSubmitted ) {
      this.submitQuiz()
    } else {
      this.refreshQuiz()
    }
  }


  submitQuiz() {
    /**
     * 1. show the banner with calculated score
     * 2. TODO: call the api 
     */
    console.log(this.quizForm)
    this.isSubmitted = true
    this.willShowResult = true

    const questions: any[] = this.quizForm.value.questions;
    this.scoreSummary = questions.reduce(
      (result, question) => {
        const choices: any[] = question.choices;
        const selectedChoice = choices.find(c => c.id === question.selectedChoiceId);

        if (selectedChoice?.correct) result.score++;
        if (!selectedChoice) result.unanswered++;

        return result;
      },
      { score: 0, unanswered: 0, total: questions.length }
    )
  }


  refreshQuiz() {
    this.isSubmitted = false
    
    const questionFormArray = this.quizForm.get('questions') as FormArray
    questionFormArray.controls.forEach((questionGroup) => {
      questionGroup.get('selectedChoiceId')?.setValue(-1)
    })
  }


  getQuestionControls() {
    return (this.quizForm.get('questions') as FormArray).controls
  }


  getChoiceControlsFromQuestion(questionGroup: AbstractControl<any>) {
    // console.log(questionGroup)
    return (questionGroup.get('choices') as FormArray).controls
  }

}
