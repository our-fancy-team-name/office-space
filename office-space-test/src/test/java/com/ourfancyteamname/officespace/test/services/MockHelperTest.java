package com.ourfancyteamname.officespace.test.services;

import java.util.Properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class MockHelperTest {

  @Test
  void mockReturn() {
    Properties test = Mockito.mock(Properties.class);
    MockHelper.mockReturn(test.isEmpty(), false);
    Assertions.assertFalse(test.isEmpty());
  }

}
