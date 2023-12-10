import { Choice } from "./choice";

export interface Question {
  id: number;
  topic: string;
  question: string;
  choices: Choice[];
}
