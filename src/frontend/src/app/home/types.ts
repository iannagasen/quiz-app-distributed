
interface Choice {
  id: number,
  value: string,
  explanation: string,
  correct: boolean
}

interface Question {
  id: number,
  topic: string,
  question: string,
  choices: Choice[]
}
