import React from 'react'
import { Container } from './Container';

const QUESTIONS_API_URL = 'https://localhost:8443';

const getData = async ():Promise<Question[]> => {
  const res = await fetch(`${QUESTIONS_API_URL}/question?topic=AWS`, {
    next: { revalidate: 0 }
  })
  console.log(res.json())
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
