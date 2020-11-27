package enums;

import com.ourfancyteamname.officespace.enums.PermissionCode;
import org.junit.Assert;
import org.junit.Test;

public class PermissionCodeTest {
  @Test
  public void name() {
    Assert.assertEquals("DELETE_USER", PermissionCode.DELETE_USER.getName());
  }
}
