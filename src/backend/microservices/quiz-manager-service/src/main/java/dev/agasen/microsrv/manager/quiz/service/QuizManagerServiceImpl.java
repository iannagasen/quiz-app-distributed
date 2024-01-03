package dev.agasen.microsrv.manager.quiz.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

import dev.agasen.microsrv.api.core.question.Question;
import dev.agasen.microsrv.api.core.quiz.Quiz;
import dev.agasen.microsrv.api.core.quiz.QuizAnswerList;
import dev.agasen.microsrv.api.core.quiz.QuizItem;
import dev.agasen.microsrv.api.event.Event;
import dev.agasen.microsrv.api.event.Event.Type;
import dev.agasen.microsrv.api.manager.QuizComposite;
import dev.agasen.microsrv.api.manager.QuizCompositeItem;
import dev.agasen.microsrv.api.manager.QuizManagerService;
import dev.agasen.microsrv.api.proxies.QuestionServiceProxy;
import dev.agasen.microsrv.api.proxies.QuizServiceProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@RestController
@RequiredArgsConstructor
@Slf4j
public class QuizManagerServiceImpl implements QuizManagerService {

  private final QuestionServiceProxy questionServiceProxy;
  private final QuizServiceProxy quizServiceProxy;
  private final KafkaTemplate<Long, Event<Long, Question>> kafkaTemplate;
  private final Scheduler publishEventScheduler;


  @Override 
  public QuizComposite submitQuiz(QuizAnswerList submittedQuiz) {
    /**
     * WORKFLOW:
     *  1. call the question service to check the answers
     *  2. save the quiz result (quiz-service) 
     *      - can we make this asynchronous to step 1)
     *  3. generate event for saving analytics
     */
    throw new UnsupportedOperationException("Unimplemented method 'submitQuiz'");
  }



  @Override
  public List<Question> generateQuiz(Long totalItems, Optional<String> topic) {
    /**
     * WORKFLOW:
     *  1. Create the logic for generating a quiz 
     *  2. Call the question service
     */
    log.info("QuizManagerServiceImpl::generateQuiz");
    return questionServiceProxy.getQuestions(topic.get());
  }



  @Override
  public QuizComposite getQuizResult(long quizId) {
    /**
     * WORKFLOW
     *  1. Get the Quiz from quiz-service
     *  2. Get each Questions from Quiz and aggregate
     *  3. Get the analytics, async from 2
     *  4. Merge the result
     */

    log.info("QuizManagerServiceImpl::getQuizResult with id: {}", quizId);
    Quiz quiz = quizServiceProxy.getQuizResult(quizId);
    
    List<QuizItem> quizItems = quiz.getQuestions();
    List<Question> questions = questionServiceProxy.getQuestionsByMultipleIds(quiz.getCommaSeparatedIds());

    List<QuizCompositeItem> questionsAsCompositeItems = 
      IntStream.range(0, questions.size())
          .mapToObj(i -> {
            var quizItem = quizItems.get(i);
            return QuizCompositeItem.builder()
                .id(quizItem.getId())
                .question(questions.get(i))
                .selectedChoiceId(quizItem.getSelectedChoiceId())
                .build();
          })
          .collect(Collectors.toList());

    return QuizComposite.builder()
        .id(quiz.getId())
        .questions(questionsAsCompositeItems)
        // missing analytics
        .build();
  }



  @Override
  public Mono<Void> createQuestion(Question question) {
    /**
     * Push an event to create a question
     */

    log.info("QuizManagerServiceImpl::createQuestion");

    var event = new Event<>(Type.CREATE, question.getId(), question);

    return Mono.fromFuture(kafkaTemplate.send("question-topic", question.getId(), event)).then();
  }



  @Override
  public List<Question> getQuestions(String topic) {
    log.info("QuizManagerServiceImpl::getQuestions");
    return questionServiceProxy.getQuestions(topic);
  }
}
