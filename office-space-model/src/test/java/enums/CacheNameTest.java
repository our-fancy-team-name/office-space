package enums;

import com.ourfancyteamname.officespace.enums.CacheName;
import org.junit.Assert;
import org.junit.Test;

public class CacheNameTest {

  @Test
  public void cacheName() {
    String[] names = {"USER_PRINCIPLE", "PERMISSIONS"};
    Assert.assertEquals("USER_PRINCIPLE", CacheName.USER_PRINCIPLE);
    Assert.assertEquals("PERMISSIONS", CacheName.PERMISSIONS);
    Assert.assertEquals("TEMPLATE", CacheName.TEMPLATE);
    Assert.assertArrayEquals(names, CacheName.NAMES);
  }
}
