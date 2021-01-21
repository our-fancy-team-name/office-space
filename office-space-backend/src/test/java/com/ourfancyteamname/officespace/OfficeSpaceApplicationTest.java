package com.ourfancyteamname.officespace;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OfficeSpaceApplicationTest {

  @InjectMocks
  private OfficeSpaceApplication application;

  @Test
  void appClassTest() {
    Assertions.assertEquals("public class com.ourfancyteamname.officespace.OfficeSpaceApplication",
        application.getClass().toGenericString());
  }

}
