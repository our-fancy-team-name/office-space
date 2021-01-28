package com.ourfancyteamname.officespace;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class OfficeSpaceApplicationTest {

  @InjectMocks
  private OfficeSpaceApplication application;

  @Test
  void appClassTest() {
    assertEquals("public class com.ourfancyteamname.officespace.OfficeSpaceApplication",
        application.getClass().toGenericString());
  }

}
