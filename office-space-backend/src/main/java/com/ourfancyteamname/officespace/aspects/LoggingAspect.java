package com.ourfancyteamname.officespace.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

  @Around("@annotation(com.ourfancyteamname.officespace.annotations.LogExecutionTime)")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    final var start = System.currentTimeMillis();
    try {
      return joinPoint.proceed();
    } finally {
      log.debug("{} in {} ms", joinPoint.toShortString(), System.currentTimeMillis() - start);
    }
  }
}
