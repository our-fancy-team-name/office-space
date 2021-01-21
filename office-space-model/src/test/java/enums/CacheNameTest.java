package enums;

import com.ourfancyteamname.officespace.enums.CacheName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CacheNameTest {

  @Test
  public void cacheName() {
    Assertions.assertEquals("USER_PRINCIPLE", CacheName.USER_PRINCIPLE);
    Assertions.assertEquals("PERMISSIONS", CacheName.PERMISSIONS);
  }
}
