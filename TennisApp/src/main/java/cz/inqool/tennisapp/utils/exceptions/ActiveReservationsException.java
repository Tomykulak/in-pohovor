package cz.inqool.tennisapp.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // HTTP 409 Conflict
public class ActiveReservationsException extends RuntimeException {
  public ActiveReservationsException(String message) {
    super(message);
  }
}