package br.com.bexs.bestroute.exception;

import org.springframework.core.NestedRuntimeException;

public class FailedToReadConnectionException extends NestedRuntimeException {
  public FailedToReadConnectionException(String message, Exception e) {
    super(message, e);
  }
}
