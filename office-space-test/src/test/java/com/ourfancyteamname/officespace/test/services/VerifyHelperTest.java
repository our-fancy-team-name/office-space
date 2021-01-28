package com.ourfancyteamname.officespace.test.services;

import static org.mockito.Mockito.mock;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class VerifyHelperTest {

  @Test
  void verifyInvokeTime() {
    var properties = mock(Properties.class);
    properties.isEmpty();
    properties.isEmpty();
    VerifyHelper.verifyInvokeTime(properties, 2).isEmpty();
  }

  @Test
  void verifyInvoke1Time() {
    var properties = mock(Properties.class);
    properties.isEmpty();
    VerifyHelper.verifyInvoke1Time(properties).isEmpty();
  }


}
