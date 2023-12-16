import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { QuestionDTO } from '../types/question.dto';
import { QuestionResult, QuizResult } from '../types/core';

@Component({
  selector: 'app-quiz-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  template: `
    <form [formGroup]="quizForm" (ngSubmit)="onSubmitForm()">
        <div formArrayName="questions" class="no-scrollbar overflow-y-auto max-h-[80vh]">
          <div *ngFor="let questionGroup of getQuestionControls(); let i = index">
            <div [formGroupName]="i"
              [ngClass]="[
                'bg-clr-secondary rounded-md text-white m-3 p-3 mb-4',
                state.isSubmitted && questionGroup.get('selectedChoiceId')?.value !== -1 ?
                questionGroup.get('selectedChoiceId')?.value === questionGroup.get('correctChoiceId')?.value
                  ? 'border-2 border-green-500'
                  : 'border-2 border-red-500'
                  : ''
              ]">
              <textarea 
                formControlName="question"
                [rows]="getRows(questionGroup.value.question)"
                class="block w-full resize-none align-bottom outline-none border-0 border-b-2 bg-inherit text-lg font-semibold focus:border-blue-400 focus:ring-0"
              ></textarea>
              <!--MULTIPLE CHOICES-->
              <div formArrayName="choices" class="pt-2 mx-auto space-y-2">
                <ng-container *ngFor="let choiceGroup of getChoiceControlsFromQuestion(questionGroup); let j = index">
                  <div [formGroupName]="j" class="flex space-x-2">
                    <div class="flex h-5 items-center">
                      <input
                        formControlName="initial"
                        type="radio"
                        class="h-4 w-4 mt-1.5 rounded-full text-primary-600 shadow-sm focus:border-primary-300 focus:ring focus:ring-primary-200 focus:ring-opacity-50 focus:ring-offset-0 disabled:cursor-not-allowed"
                        [attr.name]="'question_'+i"
                        [value]="choiceGroup.get('id')?.value"
                        [attr.disabled]="state.isSubmitted ? true : null"
                        [checked]="questionGroup.get('selectedChoiceId')?.value === choiceGroup.get('id')?.value"
                        (change)="questionGroup.patchValue({ selectedChoiceId: choiceGroup.get('id')?.value })"
                      >
                    </div>
                    <label class="text-sm">
                      <div class="font-medium text-base">{{choiceGroup.get('value')?.value}}</div>
                      <p>{{choiceGroup.get('explanation')?.value}}</p>
                    </label>
                  </div>
                </ng-container>
              </div>
            </div>
          </div>
        </div>
        <button class="bg-clr-accent w-full rounded-md py-3 mx-2 mt-5" [ngClass]="!state.isSubmitted ? 'bg-clr-accent' : 'bg-clr-modal'">
          {{!state.isSubmitted ? 'Submit' : 'Retake'}}
        </button>
      </form>
  `
})
export class QuizFormComponent implements OnInit {
  /**
   * Handles Retake, Submit qui
   */

  @Input({ required: true })
  questions!: QuestionDTO[];

  @Output()
  submitHandler = new EventEmitter<QuizResult>();

  @Output()
  retakeHandler = new EventEmitter();

  quizForm!: FormGroup;

  state: State = {
    isSubmitted: false,
    currentBtn: 'submit'
  }

  constructor(
    private formBuilder: FormBuilder,
  ) { }


  ngOnInit(): void {
    this.initializeForm();
  }

  onSubmitForm() {
    switch (this.state.currentBtn) {
      case 'submit': 
        this.submitQuiz();
        break;
      case 'retake':
        this.retakeQuiz();
        break;
      default:
        break;
    }    
  }


  private initializeForm() {
    const questionsFormGroup = this.questions.map(question => (
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


  private retakeQuiz () {
    this.state.currentBtn = 'submit';
    this.state.isSubmitted = false

    this.quizForm.reset();
    this.initializeForm();
  }

  private submitQuiz () {
    /** 
     * does the ff:
     *  1. sets the isSubmitted state to true
     *  2. emit the quiz result(TODO: think if the consumer of this would only need the score summary)
     */
    this.state.isSubmitted = true;
    this.state.currentBtn = 'retake';

    const questions = this.quizForm.value.questions as QuestionResult[];
    const scoreSummary = questions.reduce(
      (result, question) => {
        const choices: any[] = question.choices;
        const selectedChoice = choices.find(c => c.id === question.selectedChoiceId);

        if (selectedChoice?.correct) result.score++;
        if (!selectedChoice) result.unanswered++;

        return result;
      },
      { score: 0, unanswered: 0, total: questions.length }
    );

    this.submitHandler.emit({
      questions: questions,
      score: scoreSummary
    });
  }

  getQuestionControls() {
    return (this.quizForm.get('questions') as FormArray).controls
  }

  getChoiceControlsFromQuestion(questionGroup: AbstractControl<any>) {
    return (questionGroup.get('choices') as FormArray).controls
  }

  getRows(text: string): number {
    const length = text.length
    if (length <= 90) return 1;
    if (length <= 180) return 2;
    return 3;
  }
}


type State = {
  isSubmitted: boolean,
  currentBtn: 'submit' | 'retake'
}