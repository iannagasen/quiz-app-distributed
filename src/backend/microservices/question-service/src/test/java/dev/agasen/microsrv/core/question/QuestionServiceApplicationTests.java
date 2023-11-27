package dev.agasen.microsrv.core.question;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import dev.agasen.microsrv.api.core.question.Choice;
import dev.agasen.microsrv.api.core.question.Question;
import dev.agasen.microsrv.core.question.persistence.QuestionEntity;
import dev.agasen.microsrv.core.question.persistence.QuestionRepository;
import dev.agasen.microsrv.core.question.service.QuestionMapper;
import reactor.core.publisher.Mono;

@SpringBootTest(
	webEnvironment=WebEnvironment.RANDOM_PORT,
	properties={
		"logging.level.org.hibernate.SQL=TRACE",
		"logging.level.org.hibernate.type=trace",
	}
)
class QuestionServiceApplicationTests extends MySqlTestBase {

	@Autowired private WebTestClient client;
	@Autowired private QuestionRepository questionRepository;
	@Autowired private QuestionMapper questionMapper;

	private final Set<Choice> CHOICES_01 = Set.of(
		new Choice(null, "choice1", "expl1", false),
		new Choice(null, "choice2", "expl2", true),
		new Choice(null, "choice3", "expl3", false)
	);


	private final Set<Choice> ALL_FALSE_CHOICES = Set.of(
		new Choice(4L, "choice1", "expl1", false),
		new Choice(5L, "choice2", "expl2", false),
		new Choice(6L, "choice3", "expl3", false)
	);

	private final Question QUESTION_AWS_01 = new Question(1L, "AWS", "question1", CHOICES_01);
	private final Question QUESTION_AWS_02 = new Question(2L, "AWS", "question1", CHOICES_01);
	private final Question QUESTION_AWS_03 = new Question(3L, "AWS", "question1", CHOICES_01);
	
	private final Question QUESTION_AWS_INVALID = new Question(3L, "AWS", "question1", ALL_FALSE_CHOICES);



	@BeforeEach
	void setupDb() {
		questionRepository.deleteAll();
	}


	@Test
	void getQuestionsByTopic() {

		String topic = "AWS";

		assertEquals(
			0, 
			questionRepository.findAllByTopic(topic).size()
		);

		postAndVerifyQuestion(QUESTION_AWS_01, OK);
		postAndVerifyQuestion(QUESTION_AWS_02, OK);
		postAndVerifyQuestion(QUESTION_AWS_03, OK);

		assertEquals(
			3,
			questionRepository.findAllByTopic(topic).size()
		);

		getAndVerifyQuestionsByTopic(topic, OK)
			.jsonPath("$.length()").isEqualTo(3)
			.jsonPath("$[0].id").isEqualTo(1)
			.jsonPath("$[1].id").isEqualTo(2)
			.jsonPath("$[2].id").isEqualTo(3)
			.jsonPath("$[2].topic").isEqualTo("AWS");
	}


	@Test
	void getQuestionById() {

		QuestionEntity entity = questionMapper.apiToEntity(QUESTION_AWS_01);
		QuestionEntity savedEntity = questionRepository.save(entity);
		Long id = savedEntity.getId();

		assertEquals(
			true,
			questionRepository.findById(id).isPresent()
		);

		getAndVerifyQuestionById(id, OK)
			.jsonPath("$.topic").isEqualTo("AWS")
			.jsonPath("$.question").isEqualTo(QUESTION_AWS_01.getQuestion())
			.jsonPath("$.choices.length()").isEqualTo(QUESTION_AWS_01.getChoices().size());
	}


	@Test
	void createQuestion() {

		assertEquals(
			0, 
			questionRepository.findAll().size()
		);

		postAndVerifyQuestion(QUESTION_AWS_01, OK);

		assertEquals(
			1, 
			questionRepository.findAll().size()
		);

		postAndVerifyQuestion(QUESTION_AWS_INVALID, UNPROCESSABLE_ENTITY);

		assertEquals(
			1, 
			questionRepository.findAll().size()
		);
	}


	private WebTestClient.BodyContentSpec postAndVerifyQuestion(Question question, HttpStatus expectedStatus) {
		return client.post()
			.uri("/question")
			.body(Mono.just(question), Question.class)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isEqualTo(expectedStatus)
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody();
				
	}

	private WebTestClient.BodyContentSpec getAndVerifyQuestionsByTopic(String topic, HttpStatus expectedStatus) {
    return client.get()
      .uri("/question?topic=" + topic)
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus().isEqualTo(expectedStatus)
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody();
  }

	private WebTestClient.BodyContentSpec getAndVerifyQuestionById(Long id, HttpStatus expectedStatus) {
		return client.get()
			.uri("/question/" + id)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isEqualTo(expectedStatus)
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody();

	}
}
