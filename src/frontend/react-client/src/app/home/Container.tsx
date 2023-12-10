import React, { FC, ReactNode } from 'react'
import { NavBar } from './NavBar'
import MainBar from './MainBar'

interface Props {
  topic: string,
  questions: Question[]
}

export const Container:FC<Props> = ({ topic, questions }) => {
  return (
    <div className='w-full bg-clr-background text-white'>
      <MainLayout>
        <NavBar topic={topic}></NavBar>
        <MainBar topic={topic} questions={questions}/>
        <NavBar topic={topic}></NavBar>
      </MainLayout>
    </div>
  )
}


interface MainLayoutProps {
  children: [ReactNode, ReactNode, ReactNode]
}

export const MainLayout: FC<MainLayoutProps> = ({
  children: [navBar, mainBar, statBar]
}) => {
  return (
    <div className='flex min-h-screen items-stretch'>
      <div className='basis-1/4 m-2'>
        {navBar}
      </div>
      <div className='basis-1/2 m-2'>
        {mainBar}
      </div>
      <div className='basis-1/4 m-2'>
        {statBar}
      </div>
    </div>
  )
}