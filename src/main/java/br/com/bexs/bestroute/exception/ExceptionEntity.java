package br.com.bexs.bestroute.exception;

import org.springframework.http.HttpStatus;

public class ExceptionEntity {
  private HttpStatus status;
  private String message;

  public ExceptionEntity(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }
}
