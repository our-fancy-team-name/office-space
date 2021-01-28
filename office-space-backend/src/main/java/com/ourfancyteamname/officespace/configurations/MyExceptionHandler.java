package com.ourfancyteamname.officespace.configurations;

import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
  public ResponseEntity<Object> userNameNotFoundException(Exception ex) {
    return new ResponseEntity<>(getBody(HttpStatus.UNAUTHORIZED, ex, ex.getMessage()),
        HttpStatus.UNAUTHORIZED);
  }


  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> illegalException(Exception ex) {
    return new ResponseEntity<>(getBody(HttpStatus.EXPECTATION_FAILED, ex, ex.getMessage()),
        HttpStatus.EXPECTATION_FAILED);
  }

  private Map<String, Object> getBody(HttpStatus status, Exception ex, String message) {
    log.error(message, ex);
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("message", message);
    body.put("timestamp", ZonedDateTime.now());
    body.put("status", status.value());
    body.put("error", status.getReasonPhrase());
    Throwable cause = ex.getCause();
    if (cause != null) {
      body.put("exceptionCause", ex.getCause().toString());
    }
    return body;
  }
}
