package dev.agasen.microsrv.api.core.question;

public class NoCorrectAnswerException extends RuntimeException {

  public static NoCorrectAnswerException withQuestion(Question q) {
    return new NoCorrectAnswerException(
      "All Choices of Question with ID: " + q.getId() + " has zero correct tag"
    );
  }

  public NoCorrectAnswerException() {}

  public NoCorrectAnswerException(String message) {
    super(message);
  }

  public NoCorrectAnswerException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoCorrectAnswerException(Throwable cause) {
    super(cause);
  }
}
