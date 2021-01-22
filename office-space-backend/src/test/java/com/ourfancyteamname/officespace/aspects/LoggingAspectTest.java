package com.ourfancyteamname.officespace.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoggingAspectTest {

  @InjectMocks
  private LoggingAspect aspect;

  @Test
  void logExecutionTime() throws Throwable {
    ProceedingJoinPoint input = Mockito.mock(ProceedingJoinPoint.class);
    aspect.logExecutionTime(input);
    Mockito.verify(input, Mockito.times(1)).proceed();
  }
}
