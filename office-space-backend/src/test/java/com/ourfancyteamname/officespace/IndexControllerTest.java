package com.ourfancyteamname.officespace;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class IndexControllerTest {

  @InjectMocks
  private IndexController controller;

  @Test
  void shouldReturnIndex() {
    assertEquals("forward:/index.html", controller.home(null));
  }
}
