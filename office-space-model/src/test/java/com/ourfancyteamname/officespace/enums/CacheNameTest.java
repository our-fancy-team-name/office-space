package com.ourfancyteamname.officespace.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CacheNameTest {

  @Test
  void cacheName() {
    Assertions.assertEquals("USER_PRINCIPLE", CacheName.USER_PRINCIPLE);
    Assertions.assertEquals("PERMISSIONS", CacheName.PERMISSIONS);
  }
}
