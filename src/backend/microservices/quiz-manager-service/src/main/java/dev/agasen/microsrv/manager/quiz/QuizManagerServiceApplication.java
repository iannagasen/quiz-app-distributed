package dev.agasen.microsrv.manager.quiz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
public class QuizManagerServiceApplication {

	public static void main(String[] args) {
		Logger log = LoggerFactory.getLogger(QuizManagerServiceApplication.class);

		log.info("RUNNING APPLICATION");
		log.info("1");
		log.info("2");
		log.info("3");
		SpringApplication.run(QuizManagerServiceApplication.class, args);
	}

}
