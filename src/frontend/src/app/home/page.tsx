import React from 'react'
import { Container } from './Container';

const QUESTIONS_API_URL = 'http://localhost:7001';

const getData = async ():Promise<Question[]> => {
  const res = await fetch(`${QUESTIONS_API_URL}/question?topic=AWS`, {
    next: { revalidate: 0 }
  })
  return res.json();
}

export default async function Home() {

  const data = await getData();

  return (
    <div>
      <Container questions={data} topic='AWS'></Container>
    </div>
  )
}
