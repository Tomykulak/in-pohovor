package cz.inqool.tennisapp.utils.exceptions;

public class AlreadyDeletedException extends RuntimeException {
  public AlreadyDeletedException(String message) {
    super(message);
  }
}
