package enums;

import com.ourfancyteamname.officespace.enums.PermissionCode;
import org.junit.Assert;
import org.junit.Test;

public class PermissionCodeTest {
  @Test
  public void name() {
    Assert.assertEquals("USER_EDIT", PermissionCode.USER_EDIT.getName());
  }
}
