package com.ourfancyteamname.officespace.test.services;

import lombok.experimental.UtilityClass;
import org.mockito.stubbing.OngoingStubbing;

import static org.mockito.Mockito.when;

@UtilityClass
public class MockHelper {

  public <T> OngoingStubbing<T> mockReturn(T methodCall, T value) {
    return when(methodCall).thenReturn(value);
  }

}
