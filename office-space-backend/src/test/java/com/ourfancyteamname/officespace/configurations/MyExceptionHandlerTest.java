package com.ourfancyteamname.officespace.configurations;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MyExceptionHandlerTest {

  @InjectMocks
  MyExceptionHandler myExceptionHandler;

  @Test
  public void userNameNotFoundException() {
    String message = "message";
    Exception ex = new Exception(message);
    ResponseEntity<Object> result = myExceptionHandler.userNameNotFoundException(ex);
    Assert.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    Assert.assertEquals(message, Map.class.cast(result.getBody()).get("message"));
  }

  @Test
  public void userNameNotFoundExceptionWithCause() {
    String message = "message";
    Exception ex = new Exception(message, new UsernameNotFoundException(message));
    ResponseEntity<Object> result = myExceptionHandler.userNameNotFoundException(ex);
    Assert.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    Assert.assertEquals(message, Map.class.cast(result.getBody()).get("message"));
  }

  @Test
  public void illegalException() {
    String message = "message";
    Exception ex = new Exception(message);
    ResponseEntity<Object> result = myExceptionHandler.illegalException(ex);
    Assert.assertEquals(HttpStatus.EXPECTATION_FAILED, result.getStatusCode());
    Assert.assertEquals(message, Map.class.cast(result.getBody()).get("message"));
  }

}
