package br.com.bexs.bestroute.exception;

import org.springframework.http.HttpStatus;

public class BestRouteNotFoundException extends ApiException {
  public BestRouteNotFoundException(HttpStatus status, String message) {
    super(status, message);
  }
}
