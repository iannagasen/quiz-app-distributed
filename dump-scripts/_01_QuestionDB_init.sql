

CREATE TABLE IF NOT EXISTS questions (
  id          INTEGER PRIMARY KEY AUTO_INCREMENT,
  version     INTEGER NOT NULL,
  topic       VARCHAR(255),
  question    TEXT NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS choices (
  id          INTEGER PRIMARY KEY AUTO_INCREMENT,
  version     INTEGER NOT NULL,
  value       VARCHAR(255) NOT NULL,
  explanation TEXT,
  correct     BOOLEAN NOT NULL,
  question_id INTEGER,
  FOREIGN KEY (question_id) REFERENCES questions(id)
) ENGINE=InnoDB;

INSERT INTO questions (version, topic, question)
VALUES 
    (1, 'AWS', 'What does S3 stand for in AWS?'),
    (1, 'AWS', 'What service is used for computing power in AWS?'),
    (1, 'AWS', 'What service can be used to automatically scale resources based on traffic in AWS?'),
    (1, 'AWS', 'What is the AWS service for managing databases?');

INSERT INTO choices (version, value, explanation, correct, question_id)
VALUES 
    (1, 'Simple Storage Service', 'S3 stands for Simple Storage Service', true, 1),
    (1, 'Secure Storage Service', 'S3 stands for Secure Storage Service', false, 1),
    (1, 'System Storage Service', 'S3 stands for System Storage Service', false, 1),

    (1, 'EC2', 'EC2 provides computing power in AWS', true, 2),
    (1, 'S3', 'S3 provides computing power in AWS', false, 2),
    (1, 'RDS', 'RDS provides computing power in AWS', false, 2),

    (1, 'Elastic Load Balancing', 'ELB scales resources based on traffic', true, 3),
    (1, 'Auto Scaling', 'Auto Scaling scales resources based on traffic', true, 3),
    (1, 'AWS Lambda', 'AWS Lambda scales resources based on traffic', false, 3),

    (1, 'RDS', 'RDS is the AWS service for managing databases', true, 4),
    (1, 'DynamoDB', 'DynamoDB is the AWS service for managing databases', false, 4),
    (1, 'S3', 'S3 is the AWS service for managing databases', false, 4);
