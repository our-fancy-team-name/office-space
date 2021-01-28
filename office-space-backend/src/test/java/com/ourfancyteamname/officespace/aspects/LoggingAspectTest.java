package com.ourfancyteamname.officespace.aspects;

import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.mockito.Mockito.mock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class LoggingAspectTest {

  @InjectMocks
  private LoggingAspect aspect;

  @Test
  void logExecutionTime() throws Throwable {
    var input = mock(ProceedingJoinPoint.class);
    aspect.logExecutionTime(input);
    verifyInvoke1Time(input).proceed();
  }
}
