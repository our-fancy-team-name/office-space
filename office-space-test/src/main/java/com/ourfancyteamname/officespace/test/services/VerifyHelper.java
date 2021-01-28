package com.ourfancyteamname.officespace.test.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import lombok.experimental.UtilityClass;

@UtilityClass
public class VerifyHelper {

  public <T> T verifyInvokeTime(T service, int time) {
    return verify(service, times(time));
  }

  public <T> T verifyInvoke1Time(T service) {
    return verify(service);
  }
}
