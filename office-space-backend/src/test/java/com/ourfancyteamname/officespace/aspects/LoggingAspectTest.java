package com.ourfancyteamname.officespace.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class LoggingAspectTest {

  @InjectMocks
  private LoggingAspect aspect;

  @Test
  public void logExecutionTime() throws Throwable {
    ProceedingJoinPoint input = Mockito.mock(ProceedingJoinPoint.class);
    aspect.logExecutionTime(input);
    Mockito.verify(input, Mockito.times(1)).proceed();
  }
}
