package com.ourfancyteamname.officespace.test.enums;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class RestClientTestConstantTest {

  @Test
  void testConstant() {
    assertEquals("SUPER_ADMIN", RestClientConst.SUPER_ADMIN);
    assertEquals("dang", RestClientConst.USER);
  }

  @Test
  void testUrlApiBuild() {
    assertEquals("http://localhost:1501/api/endpoint", RestClientConst.urlApi(1501, "endpoint"));
    assertEquals("http://localhost:1501/api/endpoint", RestClientConst.urlApi(1501, "/endpoint"));
  }
}
