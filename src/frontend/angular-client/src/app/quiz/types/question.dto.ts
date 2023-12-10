import { ChoiceDTO } from "./choice.dto";

export interface QuestionDTO {
  id: number;
  topic: string;
  question: string;
  choices: ChoiceDTO[];
}