package enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.ourfancyteamname.officespace.enums.CacheName;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class CacheNameTest {

  @Test
  void cacheName() {
    assertEquals("USER_PRINCIPLE", CacheName.USER_PRINCIPLE);
    assertEquals("PERMISSIONS", CacheName.PERMISSIONS);
  }
}
