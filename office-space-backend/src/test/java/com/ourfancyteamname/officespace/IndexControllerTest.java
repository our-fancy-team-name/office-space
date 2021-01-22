package com.ourfancyteamname.officespace;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

  @InjectMocks
  private IndexController controller;

  @Test
  void shouldReturnIndex() {
    Assertions.assertEquals("forward:/index.html", controller.home(null));
  }
}
