import { Card } from '@/components/ui/card'
import { cn } from '@/lib/utils'
import React, { FC } from 'react'

interface Props {
  topic: string,
  questions: Question[]
}

const MainBar:FC<Props> = ({ topic, questions }) => {
  return (
    <div className='flex flex-col p-2 m-2'>
      <Header left='Topic' right={topic} />
      <QuestionManager topic={topic} questions={questions} />
    </div>
  )
}

const Header:FC<{ left: string | null, right: string | null }> = ({ left, right }) => {
  return (
    <div className='flex justify-between mb-4'>
      <div className='flex-grow text-4xl font-extrabold orange_gradient'>
        {left}
      </div>
      <div className='flex-shrink text-2xl font-extrabold'>
        {right}
      </div>
    </div>
  )
}

const QuestionManager:FC<{ topic: string, questions: Question[] }> = ({ topic, questions }) => {
  return (
    <div className={cn('no-scrollbar overflow-y-auto')}>
      {questions.map(q => <QuestionCard question={q} key={q.id}/>)}
    </div>      
  );
}

const QuestionCard:FC<{ question: Question }> = ({ question }) => {


  return (
    <Card className='bg-clr-secondary border-none text-white m-3 p-3'>
      <form>
        <div className='mb-4'>
          <label htmlFor='question'/>
          <textarea
            rows={getEstimatedRowCount(question.question.length)} 
            id='question'
            value={question.question}
            className={cn(
              "block w-full resize-none align-bottom outline-none border-0 border-b-2 bg-inherit text-lg font-semibold",
              "focus:border-blue-400 focus:ring-0"
            )}
          />
        </div>
        {/* <McqChoices choices={updatedChoices} onUpdate={handleChoiceUpdate} disabled={!isUpdating}/> */}
      </form>
    </Card>
      
  )
}

const getEstimatedRowCount = (length: number) => {
  if (length <= 90) return 1;
  if (length <= 180) return 2;
  return 3;
}


export default MainBar
