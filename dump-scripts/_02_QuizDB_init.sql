CREATE DATABASE IF NOT EXISTS `quiz-db`;


GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON *.* TO 'user'@'%';

USE `quiz-db`;

CREATE TABLE quiz (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT,
  version     INT
);

CREATE TABLE quiz_item (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT,
  version     INT,
  quiz_id     BIGINT NOT NULL,
  question_id BIGINT NOT NULL,
  choice_id   BIGINT,
  FOREIGN KEY (quiz_id) REFERENCES quiz(id)
);


INSERT INTO quiz (version) VALUES
(1),
(1),
(1);

INSERT INTO quiz_item (quiz_id, question_id, version, choice_id) VALUES
(1, 1, 1, 1), (1, 2, 1, 2), (1, 3, 1, 3), (1, 4, 1, 4), (1, 5, 1, 5),
(2, 6, 1, 6), (2, 7, 1, 7), (2, 8, 1, 8), (2, 9, 1, 9), (2, 10, 1, 10),
(3, 11, 1, 11), (3, 12, 1, 12), (3, 13, 1, 13), (3, 14, 1, 14), (3, 15, 1, 15);

