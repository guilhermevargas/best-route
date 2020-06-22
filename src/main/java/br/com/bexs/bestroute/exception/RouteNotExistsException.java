package br.com.bexs.bestroute.exception;

import org.springframework.http.HttpStatus;

public class RouteNotExistsException extends ApiException {
  public RouteNotExistsException(HttpStatus status, String message) {
    super(status, message);
  }
}
