package br.com.bexs.bestroute.exception;

import org.springframework.core.NestedRuntimeException;

public class FailedToWriteConnectionException extends NestedRuntimeException {
  public FailedToWriteConnectionException(String message, Exception ex) {
    super(message, ex);
  }
}
