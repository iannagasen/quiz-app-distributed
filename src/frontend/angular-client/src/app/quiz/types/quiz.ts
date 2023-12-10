import { Question } from "./question";

export interface Quiz {
  id: number;
  questions: Question[];
  dateTaken: Date | null;
  timeTaken: number | null;
}
