import { QuestionDTO } from "./question.dto";

export type QuestionState = QuestionDTO & {
  selectedChoiceId?: number
}