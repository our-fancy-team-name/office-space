package com.ourfancyteamname.officespace.configurations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class MyExceptionHandlerTest {

  @InjectMocks
  private MyExceptionHandler myExceptionHandler;

  @Test
  void userNameNotFoundException() {
    String message = "message";
    Exception ex = new Exception(message);
    ResponseEntity<Object> result = myExceptionHandler.userNameNotFoundException(ex);
    verify(HttpStatus.UNAUTHORIZED, result);
  }

  @Test
  void userNameNotFoundExceptionWithCause() {
    String message = "message";
    Exception ex = new Exception(message, new UsernameNotFoundException(message));
    ResponseEntity<Object> result = myExceptionHandler.userNameNotFoundException(ex);
    verify(HttpStatus.UNAUTHORIZED, result);
  }

  @Test
  void illegalException() {
    String message = "message";
    Exception ex = new Exception(message);
    ResponseEntity<Object> result = myExceptionHandler.illegalException(ex);
    verify(HttpStatus.EXPECTATION_FAILED, result);
  }

  private void verify(HttpStatus status, ResponseEntity<Object> result) {
    assertEquals(status, result.getStatusCode());
    assertEquals("message", ((Map) result.getBody()).get("message"));
  }

}
