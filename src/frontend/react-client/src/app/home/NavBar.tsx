import React, { FC } from 'react'

interface Props {
  topic: string
}

export const NavBar:FC<Props> = ({ topic }) => {
  return (
    <div className='p-2 m-2'>
      Topics: {topic}
    </div>
  )
}

