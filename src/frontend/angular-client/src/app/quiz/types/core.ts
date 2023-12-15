
export type ScoreSummary =  { 
  score: number, 
  unanswered: number, 
  total: number 
};

export type Choice = {
  id: number;
  value: string;
  explanation: string;
  correct: boolean;
}

export type QuestionResult = {
  question: string,
  choices: Choice[],
  selectedChoiceId: number,
  correctChoiceId: number
}

export type QuizResult = {
  questions: QuestionResult[]
  score: ScoreSummary
}

export interface Question {
  id: number;
  topic: string;
  question: string;
  choices: Choice[];
}

export interface Quiz {
  id: number;
  questions: Question[];
  dateTaken: Date | null;
  timeTaken: number | null;
}
