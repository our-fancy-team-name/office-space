package enums;

import com.ourfancyteamname.officespace.enums.CacheName;
import org.junit.Assert;
import org.junit.Test;

public class CacheNameTest {

  @Test
  public void cacheName() {
    Assert.assertEquals("USER_PRINCIPLE", CacheName.USER_PRINCIPLE);
    Assert.assertEquals("PERMISSIONS", CacheName.PERMISSIONS);
  }
}
