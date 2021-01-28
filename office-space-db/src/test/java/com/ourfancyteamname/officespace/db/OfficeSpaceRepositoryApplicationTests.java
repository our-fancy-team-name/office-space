package com.ourfancyteamname.officespace.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class OfficeSpaceRepositoryApplicationTests {

  @InjectMocks
  private OfficeSpaceRepositoryApplication application;

  @Test
  void appClassTest() {
    assertEquals("public class com.ourfancyteamname.officespace.db.OfficeSpaceRepositoryApplication",
        application.getClass().toGenericString());
  }
}
