package com.ourfancyteamname.officespace;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {

  @InjectMocks
  private IndexController controller;

  @Test
  public void shouldReturnIndex() {
    Assert.assertEquals("forward:/index.html", controller.home(null));
  }
}
