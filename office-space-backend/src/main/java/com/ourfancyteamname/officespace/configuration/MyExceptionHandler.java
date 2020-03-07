package com.ourfancyteamname.officespace.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class MyExceptionHandler
    extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> exception(Exception ex) {
    return new ResponseEntity<>(getBody(HttpStatus.INTERNAL_SERVER_ERROR, ex, ex.getMessage()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private Map<String, Object> getBody(HttpStatus status, Exception ex, String message) {
    log.error(message, ex);
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("message", message);
    body.put("timestamp", new Date());
    body.put("status", status.value());
    body.put("error", status.getReasonPhrase());
    body.put("exception", ex.toString());
    Throwable cause = ex.getCause();
    if (cause != null) {
      body.put("exceptionCause", ex.getCause().toString());
    }
    return body;
  }
}
