package com.ourfancyteamname.officespace.configurations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class MyExceptionHandlerTest {

  @InjectMocks
  MyExceptionHandler myExceptionHandler;

  @Test
  public void userNameNotFoundException() {
    String message = "message";
    Exception ex = new Exception(message);
    ResponseEntity<Object> result = myExceptionHandler.userNameNotFoundException(ex);
    Assertions.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    Assertions.assertEquals(message, Map.class.cast(result.getBody()).get("message"));
  }

  @Test
  public void userNameNotFoundExceptionWithCause() {
    String message = "message";
    Exception ex = new Exception(message, new UsernameNotFoundException(message));
    ResponseEntity<Object> result = myExceptionHandler.userNameNotFoundException(ex);
    Assertions.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    Assertions.assertEquals(message, Map.class.cast(result.getBody()).get("message"));
  }

  @Test
  public void illegalException() {
    String message = "message";
    Exception ex = new Exception(message);
    ResponseEntity<Object> result = myExceptionHandler.illegalException(ex);
    Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED, result.getStatusCode());
    Assertions.assertEquals(message, Map.class.cast(result.getBody()).get("message"));
  }

}
