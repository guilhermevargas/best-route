package br.com.bexs.bestroute.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ControllerAdvice
public class HandlerException {
  @ExceptionHandler(value = {ApiException.class})
  protected ResponseEntity<Object> handlerApiException(ApiException ex) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(APPLICATION_JSON);
    ExceptionEntity entity = new ExceptionEntity(ex.getStatus(), ex.getMessage());
    return new ResponseEntity<>(entity, httpHeaders, ex.getStatus());
  }

  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<Object> handlerApiException(Exception ex) {
    ex.printStackTrace();

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(APPLICATION_JSON);
    ExceptionEntity apiException = new ExceptionEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Something wrong happened");
    return new ResponseEntity<>(apiException, httpHeaders, apiException.getStatus());
  }

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  protected ResponseEntity<Object> handlerApiException(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    ExceptionEntity entity = new ExceptionEntity(BAD_REQUEST, message);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(APPLICATION_JSON);
    return new ResponseEntity<>(entity, httpHeaders, BAD_REQUEST);
  }
}
