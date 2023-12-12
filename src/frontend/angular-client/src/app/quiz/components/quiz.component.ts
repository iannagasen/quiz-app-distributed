import { Component, OnInit } from '@angular/core';
import { QuizService } from '../service/quiz.service';
import { CommonModule } from '@angular/common';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, NgForm, ReactiveFormsModule } from '@angular/forms';

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

  isSubmitted = false;
  topic: string = 'AWS'
  scoreSummary: {
    score: number,
    unanswered: number,
    total: number
  } | undefined = undefined;


  quizForm!: FormGroup;


  constructor(
    private quizService: QuizService,
    private formBuilder: FormBuilder,
  ) { }

  ngOnInit(): void {
    const questionsState = this.quizService.generateQuiz('AWS');

    const questionsFormGroup = questionsState.map(question => (
      this.formBuilder.group({
        question: new FormControl(question.question),
        selectedChoiceId: '',
        choices: this.formBuilder.array(
          question.choices.map(choice => (
            this.formBuilder.group({
              id: choice.id,
              value: choice.value,
              explanation: choice.explanation,
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


  onSubmit(form: NgForm) {
    /**
     * 1. show the banner with calculated score
     * 2. TODO: call the api 
     */
    // console.log(object)
    // console.log(form)
    this.isSubmitted = true

    // this.scoreSummary = this.questionsState.reduce(
    //   (result, question) => {
    //     const selectedChoice = question.choices.find(c => c.id === question.selectedChoiceId);

    //     if (selectedChoice?.correct) result.score ++;
    //     if (!selectedChoice) result.unanswered ++;

    //     return result;
    //   },
    //   { score: 0, unanswered: 0, total: this.questionsState.length}
    // )
  }

  onSubmitReactive() {
    console.log(this.quizForm)
  }

  getQuestionControls() {
    return (this.quizForm.get('questions') as FormArray).controls
  }

  getChoiceControlsFromQuestion(questionGroup: AbstractControl<any>) {
    // console.log(questionGroup)
    return (questionGroup.get('choices') as FormArray).controls
  }


}
