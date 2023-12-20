
## TODO: 

FRONTEND
  - [X] (12/12/23) Initialize the angular client
  - [X] (12/15/23) Divide the quiz into subcomponents
  - [ ] implement shuffling of quetions in quiz
    - use a custom shuffle pipe
  - [ ] remove selected when selected choice is clicked
  - [ ] (12/17) choice explanation should only render when quiz is submitted
  - [ ] (12/17) add a form to for generating a quiz
    - ex: quiz topic, number of questions
    - quiz-type(MCQ/Flascards) Optional - must be implemented first in backend
  - [ ] implement Drawer 
    - for menu,
  - [ ] implement "Session expires" when token expires
  - [ ] design login page
    - make the login form to the left
    - semi landing page in right
  - [ ] add registration page
  - [ ] figure out how to disable login when on testing accounts
    - maybe use environment variables to toggle

BACKEND
  - [ ] create schema for:
    - [ ] generate quiz:
    - [ ] get quiz:
  - [ ] complete swagger - springdoc api documentation
  - [ ] centralize configuration for the microservices
  - [ ] create user service and integrate with authentication server 
    - [ ] retrieve users from api/db rather than using  in memory
  - [X] [12-21-2023] Created docker compose for ELK stack
  - [ ] configure ELK Services
    - [ ] ElasticSearch
    - [ ] Logstash 
    - [ ] Kibana

DOCUMENTATION
  - [ ] create erd for different db

rename dashboard to home