package com.ourfancyteamname.officespace;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class OfficeSpaceApplicationTest {

  @InjectMocks
  private OfficeSpaceApplication application;

  @Test
  public void appClassTest() {
    Assert.assertEquals("public class com.ourfancyteamname.officespace.OfficeSpaceApplication",
        application.getClass().toGenericString());
  }

}
